package creos.simsg.api.validator.rules;

import creos.simsg.api.circlefinder.Circle;
import creos.simsg.api.circlefinder.CircleFinder;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;

import java.util.List;
import java.util.Map;


/*
    In a circle with at least one cable with a measured consumption superior to zero, exactly 2 fuses are OPEN if and only if they both belong to a same entity, that can be a dead-end. Otherwise, at most 1 fuse of the circle is OPEN.
 */
public class AllCirclesRule implements IRule {

    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        List<Circle> circles = CircleFinder.getDefault().getCircles(substation);

        var rule = new CircleRule();

        for(Circle circle: circles) {
            if (!rule.apply(circle.getFuses(), fuseStateMap)) {
                return false;
            }
        }

        return true;
    }
}
