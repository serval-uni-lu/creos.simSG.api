package duc.sg.java.validator.umatrix;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Map;

public interface IValidator {
    boolean isValid(Substation substation, Map<Fuse, State> idxColumn);
    boolean isValid(Fuse[] setOfFuses, Map<Fuse, State> idxColumn);
}
