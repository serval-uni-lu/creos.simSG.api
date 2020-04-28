package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

public interface IPowerFlow {
    Fuse[] getFuseOnMandatoryPF(Substation substation);
}
