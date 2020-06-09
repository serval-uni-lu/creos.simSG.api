package duc.sg.java.validator.rules;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;

import java.util.Map;

/*
    For all cables that have a measure consumption superior to zero, at least one of its fuses is closed
 */
public class CableRule extends AbsFuseLevelRule {

    @Override
    protected boolean p_apply(Fuse fuse, Map<Fuse, State> fuseStateMap) {
        Cable cable = fuse.getCable();

        return cable.getConsumption() == 0 ||
                fuseStateMap.get(fuse) == State.CLOSED ||
                fuseStateMap.get(fuse.getOpposite()) == State.CLOSED
                ;
    }
}
