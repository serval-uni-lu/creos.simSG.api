package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.OArrays;

import java.util.*;

class CycleFinderImpl implements CycleFinder {
    CycleFinderImpl() {}

    private Cycle addFusesToCircle(Cycle cycle, List<Fuse> toAdd) {
        Fuse[] current = cycle.getFuses();
        Fuse[] newCycle = new Fuse[current.length + toAdd.size()];

        System.arraycopy(current, 0, newCycle, 0, current.length);
        int i = current.length;
        for(Fuse f: toAdd) {
            newCycle[i] = f;
            i++;
        }

        return new CycleImp(newCycle);

    }


    private void handleInnerCircles(List<Cycle> cycles) {
        for (int i = 0; i < cycles.size(); i++) {
            for (int j = i + 1; j < cycles.size(); j++) {
                Cycle cycle1 = cycles.get(i);
                Cycle cycle2 = cycles.get(j);

                var fusesInCommon = new HashSet<Fuse>();
                var fusesDifferentC1 = new ArrayList<Fuse>();
                var fusesDifferentC2 = new ArrayList<Fuse>();

                for (Fuse fC1: cycle1.getFuses()) {
                    if(OArrays.contains(cycle2.getFuses(), fC1)) {
                        fusesInCommon.add(fC1);
                    } else {
                        fusesDifferentC1.add(fC1);
                    }
                }

                for(Fuse fC2: cycle2.getFuses()) {
                    if(OArrays.contains(cycle1.getFuses(), fC2)) {
                        fusesInCommon.add(fC2);
                    } else {
                        fusesDifferentC2.add(fC2);
                    }
                }

                if(!fusesInCommon.isEmpty()) {
                    boolean c1Longest = cycle1.getFuses().length > cycle2.getFuses().length;

                    if(c1Longest) {
                        Cycle newCycle= addFusesToCircle(cycle1, fusesDifferentC2);
                        cycles.set(i, newCycle);
                    } else {
                        Cycle newCycle = addFusesToCircle(cycle2, fusesDifferentC1);
                        cycles.set(j, newCycle);
                    }

                }



            }
        }
    }

    @Override
    public void findAndSaveCycles(Substation substation) {
        List<Cycle> cycles = new ArrayList<>();
        var processed = new HashSet<Fuse>();

        Collection<Fuse> fuses = substation.extractFuses();

        for(Fuse fuse: fuses) {
            if(!processed.contains(fuse)) {
                List<Fuse> entFuses = fuse.getOwner().getFuses();

                for (int i = 0; i < entFuses.size(); i++) {
                    for (int j = i+1; j < entFuses.size(); j++) {
                        Fuse f1 = entFuses.get(i);
                        Fuse f2 = entFuses.get(j);

                        if(!processed.contains(f1) && !processed.contains(f2)) {
                            Optional<Cycle> optCycle = DetectCycle.findCycle(f1, f2);
                            if(optCycle.isPresent()) {
                                Cycle cycle = optCycle.get();
                                cycles.add(cycle);
                                Collections.addAll(processed, cycle.getFuses());
                            }
                        }


                    }
                }

            }
        }

        handleInnerCircles(cycles);

        substation.getGrid().save(CycleUtils.getKey(substation), cycles);

    }


}
