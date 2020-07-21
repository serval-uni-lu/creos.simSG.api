package duc.sg.java.loadapproximator.uncertain.multisubs.bsrules;

import duc.sg.java.cycle.all.InitAllCycleSubs2;
import duc.sg.java.importer.json.JsonImporter;
import duc.sg.java.importer.json.ValidationException;
import duc.sg.java.model.*;
import duc.sg.java.utils.OArrays;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class GetPaths {
    private GetPaths(){}

    private static Path getPath(Substation start, Fuse startFuse, Substation end) {
        final var distanceMap = new HashMap<Fuse, Integer>();
        var toVisit = new PriorityQueue<Fuse>((Fuse f1, Fuse f2) -> {
           int distF1 = distanceMap.getOrDefault(f1, Integer.MAX_VALUE);
           int distF2 = distanceMap.getOrDefault(f2, Integer.MAX_VALUE);
           return Integer.compare(distF1, distF2);
        });

        var visited = new HashSet<Fuse>();
        var previousMap = new HashMap<Fuse, Fuse>();

        distanceMap.put(startFuse, 0);
        visited.add(startFuse);
        navigateFuse(distanceMap, toVisit, visited, previousMap, startFuse, startFuse.getOpposite());

        Fuse endFuse = null;

        while (!toVisit.isEmpty()) {
            Fuse current = toVisit.poll();
            visited.add(current);

            if(current.getOwner() instanceof Substation) {
                if (current.getOwner().equals(end)) {
                    endFuse = current;
                }
                continue;
            }

            for(Fuse next: current.getNeighbors()) {
                navigateFuse(distanceMap, toVisit, visited, previousMap, current, next);
            }
        }

        var shortestPath = new HashSet<Fuse>();

        if(previousMap.containsKey(endFuse)) {
            Fuse current = previousMap.get(endFuse);
            while(previousMap.containsKey(current)) {
                shortestPath.add(current);
                addCircleMembersFuses(start, current, shortestPath);
                current = previousMap.get(current);
            }
        }

        return new Path(startFuse, endFuse, shortestPath);

    }

//    private static Path getPath(Substation start, Fuse startFuse, Substation end) {
//        final var distanceMap = new HashMap<Entity, Integer>();
//        var toVisit = new PriorityQueue<Entity>((e1, e2) -> {
//            int distE1 = distanceMap.getOrDefault(e1, Integer.MAX_VALUE);
//            int distE2 = distanceMap.getOrDefault(e2, Integer.MAX_VALUE);
//            return Integer.compare(distE1, distE2);
//        });
//
//        var entityVisited = new HashSet<Entity>();
//        var previousMap = new HashMap<Entity, Entity>();
//
//        distanceMap.put(start, 0);
//        entityVisited.add(start);
//
//        navigate(distanceMap, toVisit, entityVisited, previousMap, start, startFuse.getOpposite().getOwner());
//
//
//        while (!toVisit.isEmpty()) {
//            Entity current = toVisit.poll();
//            entityVisited.add(current);
//
//            if(current instanceof Substation) {
//                continue;
//            }
//
//            for(Fuse fuse: current.getFuses()) {
//                Entity nextEntity = fuse.getOpposite().getOwner();
//                navigate(distanceMap, toVisit, entityVisited, previousMap, current, nextEntity);
//            }
//        }
//
//        var shortestPath = new HashSet<Cabinet>();
//
//        if(previousMap.containsKey(end)) {
//            Entity current = previousMap.get(end);
//            while(previousMap.containsKey(current)) {
//                shortestPath.add((Cabinet) current);
//                addCircleMembers(current, start, shortestPath);
//                current = previousMap.get(current);
//            }
//        }
//
//        return new Path(start, end, shortestPath);
//    }

    private static void addCircleMembers(Entity current, Substation start, HashSet<Cabinet> shortestPath) {
        for(Fuse[] circle: start.getCycles()) {
            Cabinet[] circleEnt = Stream.of(circle)
                    .map(Fuse::getOwner)
                    .distinct()
                    .filter((Entity ent) -> ent instanceof Cabinet)
                    .toArray(Cabinet[]::new);
            if (OArrays.contains(circleEnt, current)) {
                Collections.addAll(shortestPath, circleEnt);
            }
        }
    }

    private static void addCircleMembersFuses(Substation start, Fuse current, HashSet<Fuse> shortestPath) {
        for(Fuse[] circle: start.getCycles()) {
            if(OArrays.contains(circle, current)) {
                Collections.addAll(shortestPath, circle);
            }
        }
    }

    private static void navigateFuse(HashMap<Fuse, Integer> distanceMap, PriorityQueue<Fuse> toVisit, HashSet<Fuse> visited, HashMap<Fuse, Fuse> previousMap, Fuse current, Fuse next) {
        if (!visited.contains(next)) {
            int newDistance = distanceMap.get(current) + 1;
            if (newDistance < distanceMap.getOrDefault(next, Integer.MAX_VALUE)) {
                toVisit.remove(next);
                distanceMap.put(next, newDistance);
                previousMap.put(next, current);
                toVisit.add(next);
            }
        }
    }

    private static void navigate(HashMap<Entity, Integer> distanceMap, PriorityQueue<Entity> toVisit, HashSet<Entity> entityVisited, HashMap<Entity, Entity> previousMap, Entity current, Entity nextEntity) {
        if (!entityVisited.contains(nextEntity)) {
            int newDistance = distanceMap.get(current) + 1;
            if (newDistance < distanceMap.getOrDefault(nextEntity, Integer.MAX_VALUE)) {
                toVisit.remove(nextEntity);
                distanceMap.put(nextEntity, newDistance);
                previousMap.put(nextEntity, current);
                toVisit.add(nextEntity);
            }
        }
    }

    public static Collection<Path> getPaths(Substation sub1, Substation sub2) {
        if(sub1.getCycles() == null) {
            InitAllCycleSubs2.init(sub1);
        }

        var res = new ArrayList<Path>();

        for(Fuse f: sub1.getFuses()) {
            Path path = getPath(sub1, f, sub2);
            if(path.exists()) {
                res.add(path);
            }
        }

        return res;
    }

    public static Collection<List<Path>> getAllPaths(SmartGrid grid) {
        Collection<List<Substation>> components = ExtractComponents.getConnectedSubstations(grid);

        List<List<Path>> allPaths = new ArrayList<>();

        for (List<Substation> component: components) {
            List<Path> pathsComponent = new ArrayList<>();
            for (int idxFirst = 0; idxFirst < component.size(); idxFirst++) {
                for (int idxSecond = idxFirst+1; idxSecond < component.size(); idxSecond++) {
                    Substation first = component.get(idxFirst);
                    Substation second = component.get(idxSecond);

                    Collection<Path> paths = GetPaths.getPaths(first, second);
                    pathsComponent.addAll(paths);
                }
            }
            allPaths.add(pathsComponent);
        }

        return allPaths;
    }

    public static void main(String[] args) throws ValidationException {
        // Import from Json
        InputStream is = GetPaths.class
                .getClassLoader()
                .getResourceAsStream("6Subs2Components.json");
        if(is == null) {
            System.err.println("Error wile loading the file.");
            return;
        }
        var isReader = new InputStreamReader(is);

        Optional<SmartGrid> optGrid = JsonImporter.from(isReader);
        if(optGrid.isEmpty()) {
            System.err.println("No grid to analyse.");
            return;
        }

        SmartGrid grid = optGrid.get();

        Substation subs4 = grid.getSubstation("Substation 2").get();
        Substation subs5 = grid.getSubstation("Substation 3").get();

        Collection<Path> paths = getPaths(subs4, subs5);
//        paths.forEach((Path path) -> {
//            String toShow = path.getAllEntities()
//                    .stream()
//                    .map(Entity::getName)
//                    .reduce("", (String name1, String name2) -> {
//                        if(name1.equals("")) return name2;
//                        return name1 + ", " + name2;
//                    });
//            System.out.println(toShow);
//
//        });
        paths.forEach(System.out::println);

    }

}
