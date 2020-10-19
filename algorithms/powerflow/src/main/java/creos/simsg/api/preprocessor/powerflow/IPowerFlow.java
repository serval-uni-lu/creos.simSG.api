package creos.simsg.api.preprocessor.powerflow;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;

/**
 * The power flow can be represented as a collection of fuses.
 */
public interface IPowerFlow {
    Fuse[] getFuseOnMandatoryPF(Substation substation);
}
