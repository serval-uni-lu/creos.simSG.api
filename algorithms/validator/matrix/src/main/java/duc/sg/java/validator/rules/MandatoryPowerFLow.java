package duc.sg.java.validator.rules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.preprocessor.powerflow.PowerFlow2;

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
