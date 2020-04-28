package duc.sg.java.validator.rules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Collection;
import java.util.Map;

abstract class AbsFuseLevelRule implements IRule {
    @Override
    public final boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        Collection<Fuse> fuses = substation.getAllFuses();

        for(Fuse fuse: fuses) {
            if(!p_apply(fuse, fuseStateMap)) {
                return false;
            }
        }

        return true;

    }


    protected abstract boolean p_apply(Fuse fuse, Map<Fuse, State> fuseStateMap);

}
