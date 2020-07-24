package duc.sg.java.circle.all;

import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.OArrays;

import java.util.*;

class CircleFinderImpl implements CircleFinder {
    CircleFinderImpl() {}

    @Override
    public void findAndSave(Substation substation) {
        List<Circle> circles = new ArrayList<>();
        var processed = new HashSet<Fuse>();

        List<Fuse> fuses = FuseExtracter.INSTANCE.getExtracted(substation);

        for(Fuse fuse: fuses) {
            if(!processed.contains(fuse)) {
                List<Fuse> entFuses = fuse.getOwner().getFuses();

                for (int i = 0; i < entFuses.size(); i++) {
                    for (int j = i+1; j < entFuses.size(); j++) {
                        Fuse f1 = entFuses.get(i);
                        Fuse f2 = entFuses.get(j);

                        if(!processed.contains(f1) && !processed.contains(f2)) {
                            Optional<Circle> optCycle = DetectCircle.findCircle(f1, f2);
                            if(optCycle.isPresent()) {
                                Circle circle = optCycle.get();
                                circles.add(circle);
                                Collections.addAll(processed, circle.getFuses());
                            }
                        }


                    }
                }

            }
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
