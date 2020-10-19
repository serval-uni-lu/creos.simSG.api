package duc.sg.java.validator.rules;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.ArrayList;
import java.util.Map;

/*
 * If one cable has a measured prosumption different from zero, then exactly two fuses are open if and only if they
 * both belong to the same entity that can be a dead end. Otherwise, at most one fuse of the circle is open.
 */
public class CircleRule implements IRule{
    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        return false;
    }

    private Fuse[] getOpenFuses(Fuse[] fuses, Map<Fuse, State> fuseStateMap) {
        var res = new ArrayList<Fuse>(fuses.length);

        for(Fuse f: fuses) {
            if(fuseStateMap.get(f) == State.OPEN) {
                res.add(f);
            }
        }

        return res.toArray(new Fuse[0]);
    }

    @Override
    public boolean apply(Fuse[] fuses, Map<Fuse, State> fuseStateMap) {
        Fuse[] openFuses = getOpenFuses(fuses, fuseStateMap);

        if(openFuses.length <= 1) {
            return true;
        }

        if(openFuses.length == 2) {
            Entity o1 = openFuses[0].getOwner();
            Entity o2 = openFuses[1].getOwner();

            if(o1.equals(o2) && !(o1 instanceof Substation) && o1.getFuses().size() == 2) {
                return true;
            }
        }

        return false;
    }
}
