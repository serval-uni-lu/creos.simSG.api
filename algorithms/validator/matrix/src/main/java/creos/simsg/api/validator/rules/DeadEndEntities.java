package creos.simsg.api.validator.rules;

import creos.simsg.api.model.Cable;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;

import java.util.Map;

/*
    For all cables that have a measure consumption superior to zero, if one fuse belongs to a dead-end entity, then the other is CLOSED.
 */
public class DeadEndEntities extends AbsFuseLevelRule {


    @Override
    public boolean apply(Fuse fuse, Map<Fuse, State> fuseStateMap) {
        Cable cable = fuse.getCable();
        Fuse opp = fuse.getOpposite();

        boolean ownFsDE = fuse.getOwner().isAlwaysDeadEnd();
        boolean ownOppFsDE = opp.getOwner().isAlwaysDeadEnd();


       return cable.getConsumption() == 0 ||
               (!ownFsDE && !ownOppFsDE) ||
               (ownFsDE && fuseStateMap.get(opp) == State.CLOSED) ||
               (ownOppFsDE && fuseStateMap.get(fuse) == State.CLOSED);


    }
}
