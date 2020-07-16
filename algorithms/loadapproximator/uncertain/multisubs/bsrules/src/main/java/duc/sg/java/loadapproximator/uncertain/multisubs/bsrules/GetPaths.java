package duc.sg.java.loadapproximator.uncertain.multisubs.bsrules;

import duc.sg.java.importer.json.JsonImporter;
import duc.sg.java.importer.json.ValidationException;
import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GetPaths {
    private GetPaths(){}

    public static void getPaths(Substation sub1, Substation sub2) {

        final var distanceMap = new HashMap<Entity, Integer>();
        var toVisit = new PriorityQueue<Entity>((e1, e2) -> {
           int distE1 = distanceMap.getOrDefault(e1, Integer.MAX_VALUE);
           int distE2 = distanceMap.getOrDefault(e2, Integer.MAX_VALUE);
           return Integer.compare(distE1, distE2);
        });

        var entityVisited = new HashSet<Entity>();
        var previousMap = new HashMap<Entity, Entity>();

        distanceMap.put(sub1, 0);

        toVisit.add(sub1);

        while (!toVisit.isEmpty()) {
            Entity current = toVisit.poll();
            entityVisited.add(current);

            for(Fuse fuse: current.getFuses()) {
                Entity nextEntity = fuse.getOpposite().getOwner();
                if(!entityVisited.contains(nextEntity)) {
                    int newDistance = distanceMap.get(current) + 1;
                    if(newDistance < distanceMap.getOrDefault(nextEntity, Integer.MAX_VALUE)) {
                        toVisit.remove(nextEntity);
                        distanceMap.put(nextEntity, newDistance);
                        previousMap.put(nextEntity, current);
                        toVisit.add(nextEntity);
                    }
                }
            }
        }

        var shortestPath = new ArrayList<Entity>();
        Entity current = sub2;
        while(previousMap.containsKey(current)) {
            shortestPath.add(current);
            current = previousMap.get(current);
        }
        shortestPath.add(sub1);

        String names = shortestPath.stream()
                .map(Entity::getName)
                .reduce("",(s1, s2) -> {
                    if (s1.equals("")) {

                        return s2;
                    }
                    return s1 + " -> " + s2;
                });
        System.out.println(names);

    }

    public static void main(String[] args) throws ValidationException {
        // Import from Json
        InputStream is = GetPaths.class
                .getClassLoader()
                .getResourceAsStream("6Subs2Components.json");
        if(is == null) {
            System.err.println("Eror wile loading the file.");
            return;
        }
        var isReader = new InputStreamReader(is);

        Optional<SmartGrid> optGrid = JsonImporter.from(isReader);
        if(optGrid.isEmpty()) {
            System.err.println("No grid to analyse.");
            return;
        }

        SmartGrid grid = optGrid.get();

        Substation subs4 = grid.getSubstation("Substation 4").get();
        Substation subs5 = grid.getSubstation("Substation 5").get();

        getPaths(subs4, subs5);

    }

}
