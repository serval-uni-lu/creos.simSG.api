package duc.sg.java.loadapproximator.uncertain.multisubs.bsrules;

import duc.sg.java.importer.ImportationException;
import duc.sg.java.importer.json.JsonGridImporter;
import duc.sg.java.model.SmartGrid;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        Optional<SmartGrid> optGrid = JsonGridImporter.from(isReader);
        if(optGrid.isEmpty()) {
            System.err.println("No grid to analyse.");
            return;
        }

        SmartGrid grid = optGrid.get();
        List<Map<Integer, List<Path>>> allBags = BagOfPaths.getAllBags(GetPaths.getAllPaths(grid));
        System.out.println(countCuts(allBags));
    }
}
