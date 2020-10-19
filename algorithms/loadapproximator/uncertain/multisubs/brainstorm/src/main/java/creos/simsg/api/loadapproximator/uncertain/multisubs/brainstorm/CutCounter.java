package creos.simsg.api.loadapproximator.uncertain.multisubs.brainstorm;

import creos.simsg.api.model.SmartGrid;
import creos.simsg.api.transformer.ImportationException;
import creos.simsg.api.transformer.json.importer.JsonGridImporter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Counts the number
 *
 * <strong>WARNING:</strong> The algorithm has been tested only on one grid (cf.
 * <em>resources/6Subs2Components.json</em>). One might check if it works also on others. Moreover, a simpler
 * implementation might be possible (see presentation done for the technical meeting on the 17 September 2020).
 */
public class CutCounter {
    private CutCounter(){}

    // it's a magic computation...
    // I found this result by cumulative mistakes...
    // cannot explain how it works....
    private static int fromNbPath2NbCut(int nbPath) {
        int determinant = 1 + (nbPath * 8);
        return (int) (-1 + Math.sqrt(determinant)) / 2;
    }

    public static int count(Map<Integer, List<Path>> mapBagPath) {
        int counter = 0;

        for(var entry: mapBagPath.entrySet()) {
            var pathsInBag = entry.getValue();
            int nbCutForPath;
            if (pathsInBag.size() == 1) {
                nbCutForPath = 1;
            } else {
                nbCutForPath = fromNbPath2NbCut(pathsInBag.size());
            }
            entry.getValue().forEach((Path path) -> path.setNbCut(nbCutForPath));
            counter += nbCutForPath;
        }

        return counter;

    }

    public static int countCuts(List<Map<Integer, List<Path>>> bags) {
        int counter = 0;

        for(Map<Integer, List<Path>> bag: bags) {
            counter += count(bag);
        }

        return counter;
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
        List<Map<Integer, List<Path>>> allBags = BagOfPaths.getAllBags(GetPaths.getAllPaths(grid));
        allBags.forEach((Map<Integer, List<Path>> bag) -> {
            bag.forEach((Integer key, List<Path> paths) -> {
                System.out.println(key + ": ");
                paths.forEach(System.out::println);
                System.out.println("-------");
            });
        });
//        System.out.println(countCuts(allBags));
    }
}
