package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.*;

public class InitAllCycleSubs2 {
    private InitAllCycleSubs2() {}

    public static void init(Substation substation) {
        List<Fuse[]> cycles = new ArrayList<>();
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
                            Fuse[] cycle = CycleFinder3.findCycle(f1, f2);
                            if(cycle.length > 1) {
                                cycles.add(cycle);
                                Collections.addAll(processed, cycle);
                            }
                        }


                    }
                }

            }
        }

        substation.setCycles(cycles);
    }

}
