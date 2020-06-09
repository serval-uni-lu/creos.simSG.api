package duc.sg.java.cycle.current;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.OArrays;

import java.util.Collection;

public class CycleDetection {
    private CycleDetection(){}


    public static Fuse[] cycleFrom(Substation substation, Fuse start) {
        Collection<Fuse[]> cycles = substation.getCycles();

        Fuse[] foundCycle = null;
        for(Fuse[] cycle: cycles) {
            if(OArrays.contains(cycle, start)) {
                foundCycle = cycle;
                break;
            }
        }

        if(foundCycle == null) {
            return new Fuse[0];
        }

        for (int i = 0; i < foundCycle.length; i++) {
            Fuse fc = foundCycle[i];
            if (!fc.isClosed()) {
                return new Fuse[0];
            }

            // put end of the circle in 1st position
            if (!fc.equals(start) && fc.getOwner().equals(start.getOwner()) && i>0) {
                foundCycle[i] = foundCycle[0];
                foundCycle[0] = fc;
            }

        }



        return foundCycle;

    }
}
