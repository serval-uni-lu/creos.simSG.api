package creos.simsg.api.loadapproximator.uncertain.bsrules;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;
import creos.simsg.api.preprocessor.powerflow.PowerFlow2;

/**
 * List all uncertain fuses, and mark certain those that belong to the mandatory power flow.
 */
public class UFuseDetector {

    private UFuseDetector() {}

    public static final void detectAndModifyUFuses(Substation substation) {
        Fuse[] mPF = new PowerFlow2().getFuseOnMandatoryPF(substation);
        for(Fuse f: mPF) {
            f.closeFuse();
            f.getStatus().makeCertain();
        }; //fuses should always be closed, no need to check the validity of the rule
    }

}
