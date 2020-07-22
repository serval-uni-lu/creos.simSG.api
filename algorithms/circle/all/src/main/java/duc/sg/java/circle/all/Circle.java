package duc.sg.java.circle.all;

import duc.sg.java.model.Fuse;

public interface Circle {
    Fuse[] getFuses();

    /**
     * A circle is valid if all its fuses are closed. Otherwise, it means that the circle is not effective
     *
     * @return true if the circle is effective
     */
    boolean isValid();

    Fuse getOtherEndPoint(Fuse start);
}
