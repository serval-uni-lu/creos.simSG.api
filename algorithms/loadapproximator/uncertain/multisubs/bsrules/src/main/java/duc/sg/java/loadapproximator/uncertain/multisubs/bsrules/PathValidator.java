package duc.sg.java.loadapproximator.uncertain.multisubs.bsrules;

import duc.sg.java.circlefinder.Circle;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.State;
import duc.sg.java.navigation.bfs.BFSFuse;
import duc.sg.java.transformer.ImportationException;
import duc.sg.java.transformer.json.importer.JsonGridImporter;
import duc.sg.java.utils.OArrays;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class PathValidator {
    private PathValidator() {}

    private static Set<Fuse> openFuses(Path path, Map<Fuse, State> configuration) {
        return path.getFuses()
                .stream()
                .filter((Fuse fuse) -> configuration.get(fuse) == State.OPEN)
                .collect(Collectors.toSet());
    }

    private static Set<Fuse> openFuses(Map<Integer, List<Path>> bag, Map<Fuse, State> configuration) {
        final var res = new HashSet<Fuse>();
        bag.values()
                .forEach(paths -> paths.forEach(path -> res.addAll(openFuses(path, configuration))));
        return res;
    }

    private static Optional<Circle> getCircleWith(List<Circle> circles, Fuse toSearch) {
        for(Circle circle: circles) {
            if(OArrays.contains(circle.getFuses(), toSearch)) {
                return Optional.of(circle);
            }
        }
        return Optional.empty();
    }

    private static int getNbActualCount(Map<Integer, List<Path>> bag, Set<Fuse> openFuses) {
        int actual = 0;

        for(List<Path> b: bag.values()) {
            if(b.size() == 1) {
                actual += getNbActualCount(b.get(0), openFuses);
            } else {
//                for(Path bp: b) {
//                    actual += getNbActualCount(bp, openFuses);
//                }
                actual += getNbActualCount(b, openFuses);
            }
        }


        return actual;
    }

    private static int getNbActualCount(List<Path> paths, Set<Fuse> openFuses) {
        int actual = 0;

        var visitedFuse = new HashSet<Fuse>();

        for(Path path: paths) {

            var openFusesInPath = path.getFuses()
                    .stream()
                    .filter(openFuses::contains)
                    .collect(Collectors.toList());

            for(Fuse fuse: openFusesInPath) {
                if(!visitedFuse.contains(fuse)) {
                    visitedFuse.add(fuse);
                    Optional<Circle> optCircle = getCircleWith(path.getCircles(), fuse);
                    if(optCircle.isEmpty()) {
                        actual++;
                    } else {
                        Circle circle = optCircle.get();
                        Collections.addAll(visitedFuse, circle.getFuses());
                        int nbDistinctEntity = (int) Arrays.stream(circle.getFuses())
                                .filter(openFuses::contains)
                                .map(Fuse::getOwner)
                                .distinct()
                                .count();
                        if(nbDistinctEntity > 1) {
                            actual += Math.round(nbDistinctEntity/2.);
                        }
                    }

                }
            }

        }




        return actual;
    }

    private static int getNbActualCount(Path path, Set<Fuse> openFuses) {
        int actual = 0;

        var visitedFuse = new HashSet<Fuse>(openFuses.size());

        var openFusesInPath = path.getFuses()
                .stream()
                .filter(openFuses::contains)
                .collect(Collectors.toList());

        for(Fuse fuse: openFusesInPath) {
            if(!visitedFuse.contains(fuse)) {
                visitedFuse.add(fuse);
                Optional<Circle> optCircle = getCircleWith(path.getCircles(), fuse);
                if(optCircle.isEmpty()) {
                    actual++;
                } else {
                    Circle circle = optCircle.get();
                    Collections.addAll(visitedFuse, circle.getFuses());
                    int nbDistinctEntity = (int) Arrays.stream(circle.getFuses())
                            .filter(openFuses::contains)
                            .map(Fuse::getOwner)
                            .distinct()
                            .count();
                    if(nbDistinctEntity > 1) {
                        actual += Math.round(nbDistinctEntity/2.);
                    }
                }
            }
        }

        return actual;
    }


    public static boolean isValid(Path path, Map<Fuse, State> configuration) {
        int expectedNbCut = path.getNbCut();

        Set<Fuse> openFuses = openFuses(path, configuration);
        int actualCut = getNbActualCount(path, openFuses);
        System.out.println(path + " / " + expectedNbCut + " / " + actualCut);

        return expectedNbCut == actualCut;
    }

    public static boolean isValid(Map<Integer, List<Path>> bag, Map<Fuse, State> configuration) {
        int expectedNbCut = CutCounter.count(bag);

        Set<Fuse> openFuses = openFuses(bag, configuration);
        int actualCut = getNbActualCount(bag, openFuses);
        return expectedNbCut == actualCut;
    }

    public static void main(String[] args) throws ImportationException {
        // Import from Json
        InputStream is = GetPaths.class
                .getClassLoader()
                .getResourceAsStream("6Subs2Components.json");
        if(is == null) {
            System.err.println("Error wile loading the file.");
            return;
        }
        var isReader = new InputStreamReader(is);

        Optional<SmartGrid> optGrid = JsonGridImporter.INSTANCE.from(isReader);
        if(optGrid.isEmpty()) {
            System.err.println("No grid to analyse.");
            return;
        }

        SmartGrid grid = optGrid.get();
        Collection<List<Path>> allPaths = GetPaths.getAllPaths(grid);
        List<Map<Integer, List<Path>>> allBags = BagOfPaths.getAllBags(allPaths);
        CutCounter.countCuts(allBags);

        var fusesToOpen = new HashSet<String>();
        Collections.addAll(fusesToOpen, "Fuse 38", "Fuse 42", "Fuse 39", "Fuse 2", "Fuse 16", "Fuse 30", "Fuse 32", "Fuse 10", "Fuse ");


        var configuration = new HashMap<Fuse, State>();
        BFSFuse.INSTANCE.navigate(grid.getSubstation("Substation 4").get(), (Fuse f) -> {
            if(fusesToOpen.contains(f.getName())) {
                configuration.put(f, State.OPEN);
            } else {
                configuration.put(f, State.CLOSED);
            }
        });

        BFSFuse.INSTANCE.navigate(grid.getSubstation("Substation 1").get(), (Fuse f) -> {
            if(fusesToOpen.contains(f.getName())) {
                configuration.put(f, State.OPEN);
            } else {
                configuration.put(f, State.CLOSED);
            }
        });

        for(Map<Integer, List<Path>> bags: allBags) {
            if(!PathValidator.isValid(bags, configuration)) {
                System.out.println("Ouch...");
            } else {
                System.out.println("OK");
            }
        }

    }

}
