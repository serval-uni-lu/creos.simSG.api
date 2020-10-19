package creos.simsg.api.circlefinder;

import creos.simsg.api.model.Substation;

import java.util.List;
import java.util.Optional;

public interface CircleFinder {
    static CircleFinder getDefault() {
        return CircleFinderImpl.INSTANCE;
    }

    /**
     * Detect all circle that are accessible from a substation and saved then in the shared memory.
     * Overwrites the shared memory.
     *
     * @param substation
     */
    void findAndSave(Substation substation);

    default List<Circle> getCircles(Substation substation) {
        Optional<Object> optCircles = substation.getGrid().retrieve(CircleUtils.getKey(substation));
        if(optCircles.isEmpty()) {
            findAndSave(substation);
            optCircles = substation.getGrid().retrieve(CircleUtils.getKey(substation));
        }

        return (List<Circle>) optCircles.get();
    }

}
