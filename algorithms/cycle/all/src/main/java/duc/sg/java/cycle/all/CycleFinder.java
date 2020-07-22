package duc.sg.java.cycle.all;

import duc.sg.java.model.Substation;

import java.util.List;
import java.util.Optional;

public interface CycleFinder {
    static CycleFinder getDefault() {
        return new CycleFinderImpl();
    }

    /**
     * Detect all cycles that are accessible from a substation and saved then in the shared memory.
     *
     * @param substation
     */
    void findAndSaveCycles(Substation substation);

    default List<Cycle> getCycles(Substation substation) {
        Optional<Object> optCircles = substation.getGrid().retrieve(CycleUtils.getKey(substation));
        if(optCircles.isEmpty()) {
            findAndSaveCycles(substation);
            optCircles = substation.getGrid().retrieve(CycleUtils.getKey(substation));
        }

        return (List<Cycle>) optCircles.get();
    }

}
