package duc.sg.java.validator.rules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.List;
import java.util.Map;

/*
    For all substations, at least one fuse should be closed.
 */
public class SubstationRule implements IRule {
    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        List<Fuse> subsFuses = substation.getFuses();

        for(Fuse fuse: subsFuses) {
            if(fuseStateMap.get(fuse) == State.CLOSED) {
                return true;
            }
        }

        return false;
    }
}
