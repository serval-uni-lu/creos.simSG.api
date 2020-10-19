package creos.simsg.api.loadapproximator.uncertain.multisubs.brainstorm;

import creos.simsg.api.model.Cabinet;

import java.util.*;

/**
 * A bag of paths is a collection of paths (cf. {@link Path}) that share cabinets and fuses. Between two random
 * bags, the intersection of cabinets and the intersection of fuses equal the empty set.
 */
public class BagOfPaths {
    private BagOfPaths(){}

    public static List<Map<Integer, List<Path>>> getAllBags(Collection<List<Path>> allPaths) {
        var res = new ArrayList<Map<Integer, List<Path>>>(allPaths.size());

        for(List<Path> paths: allPaths) {
            res.add(getBagOfPaths(paths));
        }

        return res;
    }

    public static Map<Integer, List<Path>> getBagOfPaths(Collection<Path> paths) {
        int nextBagId = 0;


        var mapCabBag = new HashMap<Cabinet, Integer>();
        var mapBagPath = new HashMap<Integer, List<Path>>();

        for(Path path: paths) {
            int bagId = -1;
            for(Cabinet c: path.getCabinets()) {
                if(mapCabBag.containsKey(c)) {
                    bagId = mapCabBag.get(c);
                    break;
                }
            }
            if(bagId == -1) {
                bagId = nextBagId;
                nextBagId++;
            }

            mapBagPath.compute(bagId, (Integer key, List<Path> currentVal) -> {
                List<Path> res;
                if (currentVal == null) {
                    res = new ArrayList<>(1);
                } else {
                    res = new ArrayList<Path>(currentVal);
                }
                res.add(path);
                return res;
            });

            for(Cabinet c: path.getCabinets()) {
                mapCabBag.put(c, bagId);
            }
        }

        return mapBagPath;
    }



}
