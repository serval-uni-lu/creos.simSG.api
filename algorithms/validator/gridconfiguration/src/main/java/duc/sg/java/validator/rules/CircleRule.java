package duc.sg.java.validator.rules;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.ArrayList;
import java.util.Map;

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

            if(!o1.equals(o2) || !o1.mightBeDeadEnd()) {
                return false;
            }
        }

        return false;
    }
}
