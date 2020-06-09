package duc.sg.java.validator.rules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Collection;
import java.util.Map;


/*
    In a circle with at least one cable with a measured consumption superior to zero, exactly 2 fuses are OPEN if and only if they both belong to a same entity, that can be a dead-end. Otherwise, at most 1 fuse of the circle is OPEN.
 */
public class AllCirclesRule implements IRule {

    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        Collection<Fuse[]> circles = substation.getCycles();

        var rule = new CircleRule();

        for(Fuse[] circle: circles) {
            if (!rule.apply(circle, fuseStateMap)) {
                return false;
            }
        }

        return true;
    }
}
