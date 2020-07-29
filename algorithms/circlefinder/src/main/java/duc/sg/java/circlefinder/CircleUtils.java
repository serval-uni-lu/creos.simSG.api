package duc.sg.java.circlefinder;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.OArrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static List<Circle> findCyclesWith(List<Circle> circles, Fuse with) {
        return circles.stream()
                .filter((Circle circle) -> OArrays.contains(circle.getFuses(), with))
                .collect(Collectors.toList());
    }


    public static List<Circle> circlesWith(Substation substation, Fuse fuse) {
        Optional<Object> optCycle = substation.getGrid()
                .retrieve(CircleUtils.getKey(substation));

        if(optCycle.isEmpty()) {
            return new ArrayList<>(0);
        }

        return findCyclesWith((List<Circle>) optCycle.get(), fuse);
    }
}
