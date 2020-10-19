package creos.simsg.api.validator.rules;

import creos.simsg.api.model.Cable;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;

import java.util.Map;

/*
    For all cables that have a measure consumption superior to zero, at least one of its fuses is closed
 */
public class CableRule extends AbsFuseLevelRule {

    @Override
    public boolean apply(Fuse fuse, Map<Fuse, State> fuseStateMap) {
        Cable cable = fuse.getCable();
        var flag = cable.getConsumption() == 0 ||
                fuseStateMap.get(fuse) == State.CLOSED ||
                fuseStateMap.get(fuse.getOpposite()) == State.CLOSED
        ;

        return flag;
    }
}
