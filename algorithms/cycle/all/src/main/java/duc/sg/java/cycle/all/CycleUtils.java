package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.OArrays;

import java.util.Collection;
import java.util.Optional;

public class CycleUtils {
    private CycleUtils(){}

    static String KEY_BASE = "CycleFinder_";

    /**
     * Compute the key used for the shared memory
     * @param substation
     * @return
     */
    static String getKey(Substation substation) {
        return KEY_BASE + substation.getName();
    }

    public static Optional<Cycle> findCycleWith(Collection<Cycle> cycles, Fuse with) {
        for (Cycle cycle : cycles) {
            if (OArrays.contains(cycle.getFuses(), with)) {
                return Optional.of(cycle);
            }
        }
        return Optional.empty();
    }


    public static Optional<Cycle> cycleFrom(Substation substation, Fuse start) {
        Optional<Object> optCycle = substation.getGrid()
                .retrieve(CycleUtils.getKey(substation));

        if(optCycle.isEmpty()) {
            return Optional.empty();
        }

        Collection<Cycle> cycles = (Collection<Cycle>) optCycle.get();
        return findCycleWith(cycles, start);
    }
}
