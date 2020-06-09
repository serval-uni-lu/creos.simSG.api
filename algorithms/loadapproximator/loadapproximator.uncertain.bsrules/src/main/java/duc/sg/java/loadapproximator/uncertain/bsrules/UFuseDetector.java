package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.preprocessor.powerflow.PowerFlow2;

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
