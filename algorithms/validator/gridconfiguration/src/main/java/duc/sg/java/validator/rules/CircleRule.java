package duc.sg.java.validator.rules;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Collection;
import java.util.Map;


/*
    In a circle with at least one cable with a measured consumption superior to zero, exactly 2 fuses are OPEN if and only if they both belong to a same entity, that can be a dead-end. Otherwise, at most 1 fuse of the circle is OPEN.
 */
class CircleRule implements IRule {
    private Fuse[] extractOPenFuses(Fuse[] circle, Map<Fuse, State> fuseStateMap) {
        Fuse[] res = new Fuse[0];

        for(Fuse c: circle) {
            if(fuseStateMap.get(c) == State.OPEN) {
                Fuse[] newRes = new Fuse[res.length + 1];
                System.arraycopy(res, 0, newRes, 0, res.length);
                res = newRes;
                res[res.length - 1 ] = c;
            }
        }

        return res;
    }

    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        Collection<Fuse[]> circles = substation.getCycles();

        for(Fuse[] circle: circles) {
            Fuse[] open = extractOPenFuses(circle, fuseStateMap);

            if(open.length > 2) {
                return false;
            }

            if(open.length == 2) {
                Entity o1 = open[0].getOwner();
                Entity o2 = open[1].getOwner();

                if(!o1.equals(o2) || !o1.mightBeDeadEnd()) {
                    return false;
                }
            }
        }

        return true;
    }
}
