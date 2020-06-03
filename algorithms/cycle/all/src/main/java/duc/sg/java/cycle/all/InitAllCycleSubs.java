package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.*;

public class InitAllCycleSubs {
    private InitAllCycleSubs() {}

    public static void init(Substation substation) {
        List<Fuse[]> cycles = new ArrayList<>();
        var processed = new HashSet<Fuse>();

        Collection<Fuse> fuses = substation.extractFuses();

        for(Fuse fuse: fuses) {
            if(!processed.contains(fuse)) {
                Fuse[] cycle = AllCycle.circleFrom(fuse);
                if(cycle.length > 0) {
                    cycles.add(cycle);
                    Collections.addAll(processed, cycle);
                }
            }
        }

        substation.setCycles(cycles);
    }

}
