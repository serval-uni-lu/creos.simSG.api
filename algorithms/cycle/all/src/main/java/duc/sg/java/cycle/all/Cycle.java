package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;

public interface Cycle {
    Fuse[] getFuses();

    /**
     * A circle is valid if all its fuses are closed. Otherwise, it means that the cycle is not effective
     *
     * @return true if the cycle is effective
     */
    boolean isValid();

    Fuse getOtherEndPoint(Fuse start);
}
