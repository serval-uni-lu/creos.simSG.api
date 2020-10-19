package creos.simsg.api.circlefinder;

import creos.simsg.api.extractor.FuseExtractor;
import creos.simsg.api.model.Entity;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;
import creos.simgsg.api.utils.OArrays;

import java.util.*;

class CircleFinderImpl implements CircleFinder {
    static final CircleFinder INSTANCE = new CircleFinderImpl();

    private CircleFinderImpl() {}

    /**
     * This function retrieve all circles that exist from one substation.
     * To perform that, we simulate the power flow by navigating through the fuses using a
     * <a href="https://en.wikipedia.org/wiki/Breadth-first_search">BFS</a> strategy.
     *
     * We tried to implement this algorithm by navigating through entities. The algorithm's result contained
     * several time a same circle. So we needed to add an extra step to filter out redundant circles. The performance
     * different might be insignificant. But one can implement this algorithm and compare the performance. However,
     * a possible advantage of this approach would be the readability of the code and its maintainability.
     *
     *
     * @param substation root element of the topology
     */
    @Override
    public void findAndSave(Substation substation) {
        List<Circle> circles = new ArrayList<>();
        var fuseInCircle = new HashSet<Fuse>();

        // Extract fuses in BFS order
        // Means that all fuses that belong to a same entities are grouped next to each other.
        List<Fuse> fuses = FuseExtractor.INSTANCE.getExtracted(substation);

        int i=0;
        while(i< fuses.size()) {
            Fuse currentFuse = fuses.get(i);
            Entity currentEnt = currentFuse.getOwner();

            long nbUnprocFuses = currentEnt.getFuses()
                    .stream()
                    .filter((Fuse f) -> !fuseInCircle.contains(f))
                    .count();

            // For each entity, for all combination of 2 fuses, check if it exists a "path"
            // that do not go through a substation and not by the entity itself
            // we remove the current fuse as no circle can start
            if(currentEnt.getFuses().size() >= 2 && nbUnprocFuses != 0) {
                List<Fuse> entFuses = currentEnt.getFuses();
                for (int idx1st = 0; idx1st < entFuses.size(); idx1st++) {
                    if(currentEnt instanceof Substation || !entFuses.get(idx1st).equals(currentFuse)) {
                        for (int idx2nd = idx1st + 1; idx2nd < entFuses.size(); idx2nd++) {
                            if(currentEnt instanceof Substation || !entFuses.get(idx2nd).equals(currentFuse)) {
                                Fuse first = entFuses.get(idx1st);
                                Fuse second = entFuses.get(idx2nd);

                                Optional<Circle> optCycle = DetectCircle.findCircle(first, second);
                                if(optCycle.isPresent()) {
                                    Circle circle = optCycle.get();
                                    circles.add(circle);
                                    Collections.addAll(fuseInCircle, circle.getFuses());
                                }

                            }
                        }
                    }
                }
            }

            i += currentEnt.getFuses().size();
        }


        handleInnerCircles(circles);
        substation.getGrid().save(CircleUtils.getKey(substation), circles);
    }

    private Circle addFusesToCircle(Circle circle, List<Fuse> toAdd) {
        Fuse[] current = circle.getFuses();
        Fuse[] newCycle = new Fuse[current.length + toAdd.size()];

        System.arraycopy(current, 0, newCycle, 0, current.length);
        int i = current.length;
        for(Fuse f: toAdd) {
            newCycle[i] = f;
            i++;
        }

        return new CircleImp(newCycle);

    }


    /**
     * Detect possible inner circles, and add all fuses of the inner circle to the one that contains it.
     *
     * @param circles all circles of a topology
     */
    private void handleInnerCircles(List<Circle> circles) {
        for (int i = 0; i < circles.size(); i++) {
            for (int j = i + 1; j < circles.size(); j++) {
                Circle circle1 = circles.get(i);
                Circle circle2 = circles.get(j);

                var fusesInCommon = new HashSet<Fuse>();
                var fusesDifferentC1 = new ArrayList<Fuse>();
                var fusesDifferentC2 = new ArrayList<Fuse>();

                for (Fuse fC1: circle1.getFuses()) {
                    if(OArrays.contains(circle2.getFuses(), fC1)) {
                        fusesInCommon.add(fC1);
                    } else {
                        fusesDifferentC1.add(fC1);
                    }
                }

                for(Fuse fC2: circle2.getFuses()) {
                    if(OArrays.contains(circle1.getFuses(), fC2)) {
                        fusesInCommon.add(fC2);
                    } else {
                        fusesDifferentC2.add(fC2);
                    }
                }

                if(!fusesInCommon.isEmpty()) {
                    boolean c1Longest = circle1.getFuses().length > circle2.getFuses().length;

                    if(c1Longest) {
                        Circle newCircle = addFusesToCircle(circle1, fusesDifferentC2);
                        circles.set(i, newCircle);
                    } else {
                        Circle newCircle = addFusesToCircle(circle2, fusesDifferentC1);
                        circles.set(j, newCircle);
                    }

                }



            }
        }
    }




}
