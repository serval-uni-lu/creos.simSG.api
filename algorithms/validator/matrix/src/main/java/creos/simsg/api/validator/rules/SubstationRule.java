package creos.simsg.api.validator.rules;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;

import java.util.List;
import java.util.Map;

/*
    For all substations, at least one fuse should be closed.
 */
public class SubstationRule implements IRule {
    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        List<Fuse> subsFuses = substation.getFuses();

        boolean allCablesWoCons = true;
        for(Fuse fuse: subsFuses) {
            if(fuseStateMap.get(fuse) == State.CLOSED) {
                return true;
            }
            allCablesWoCons = allCablesWoCons & fuse.getCable().getConsumption() == 0;
        }

        return allCablesWoCons;
    }
}
