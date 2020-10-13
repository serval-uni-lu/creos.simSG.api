package duc.sg.java.validator.matrix;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.validator.rules.IRule;
import duc.sg.java.validator.rules.Rules;

import java.util.Map;

public class CircleValidator implements IValidator {
    @Override
    public boolean isValid(Substation substation, Map<Fuse, State> idxColumn, IRule... iRules) {
        return true;
    }

    @Override
    public boolean isValid(Fuse[] setOfFuses, Map<Fuse, State> idxColumn) {
        return Rules.getCircleRules().apply(setOfFuses, idxColumn);
    }
}
