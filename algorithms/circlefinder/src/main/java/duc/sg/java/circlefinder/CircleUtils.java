package duc.sg.java.circlefinder;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.OArrays;

import java.util.Collection;
import java.util.Optional;

public class CircleUtils {
    private CircleUtils(){}

    static String KEY_BASE = "CycleFinder_";

    /**
     * Compute the key used for the shared memory
     * @param substation
     * @return
     */
    static String getKey(Substation substation) {
        return KEY_BASE + substation.getName();
    }

    public static Optional<Circle> findCycleWith(Collection<Circle> circles, Fuse with) {
        for (Circle circle : circles) {
            if (OArrays.contains(circle.getFuses(), with)) {
                return Optional.of(circle);
            }
        }
        return Optional.empty();
    }


    public static Optional<Circle> circleFrom(Substation substation, Fuse start) {
        Optional<Object> optCycle = substation.getGrid()
                .retrieve(CircleUtils.getKey(substation));

        if(optCycle.isEmpty()) {
            return Optional.empty();
        }

        Collection<Circle> circles = (Collection<Circle>) optCycle.get();
        return findCycleWith(circles, start);
    }
}
