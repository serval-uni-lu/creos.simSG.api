package duc.sg.java.circlefinder;

import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;

public interface Circle {
    Fuse[] getFuses();

    /**
     * A circle is valid if all its fuses are closed. Otherwise, it means that the circle is not effective
     *
     * @return true if the circle is effective
     */
    boolean isEffective();
    boolean isEffective(Configuration configuration);

    Fuse getOtherEndPoint(Fuse start);
}
