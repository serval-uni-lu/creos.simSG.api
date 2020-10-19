package creos.simsg.api.validator.rules;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;
import creos.simsg.api.preprocessor.powerflow.PowerFlow2;

import java.util.Map;

public class MandatoryPowerFLow implements IRule {
    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        Fuse[] alwaysOpen = new PowerFlow2().getFuseOnMandatoryPF(substation);

        for(Fuse fuse: alwaysOpen) {
            if(fuse.getCable().getConsumption() > 0 && fuseStateMap.get(fuse) == State.OPEN) {
                return false;
            }
        }

        return true;
    }
}
