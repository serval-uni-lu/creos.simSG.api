package duc.sg.java.validator.matrix;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.validator.rules.IRule;

import java.util.Map;

public interface IValidator {
    boolean isValid(Substation substation, Map<Fuse, State> idxColumn, IRule... iRules);
    boolean isValid(Fuse[] setOfFuses, Map<Fuse, State> idxColumn);
}
