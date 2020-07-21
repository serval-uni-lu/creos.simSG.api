package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.*;

class CycleFinderImpl implements CycleFinder {
    CycleFinderImpl() {}

    @Override
    public void getAndSaveCycles(Substation substation) {
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

        substation.getGrid().save(CycleUtils.getKey(substation), cycles);

    }


}
