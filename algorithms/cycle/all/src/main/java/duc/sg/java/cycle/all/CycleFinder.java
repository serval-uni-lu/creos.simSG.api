package duc.sg.java.cycle.all;

import duc.sg.java.model.Substation;

public interface CycleFinder {
    static CycleFinder getDefault() {
        return new CycleFinderImpl();
    }

    /**
     * Detect all cycles that are accessible from a substation and saved then in the shared memory.
     *
     * @param substation
     */
    void getAndSaveCycles(Substation substation);

}
