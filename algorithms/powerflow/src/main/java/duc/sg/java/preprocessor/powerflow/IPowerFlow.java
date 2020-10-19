package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

/**
 * The power flow can be represented as a collection of fuses.
 */
public interface IPowerFlow {
    Fuse[] getFuseOnMandatoryPF(Substation substation);
}
