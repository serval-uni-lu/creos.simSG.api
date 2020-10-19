package creos.simsg.api.validator;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;
import creos.simsg.api.validator.rules.IRule;

import java.util.Map;

public interface IValidator {
    boolean isValid(Substation substation, Map<Fuse, State> idxColumn, IRule... iRules);
    boolean isValid(Fuse[] setOfFuses, Map<Fuse, State> idxColumn);
}
