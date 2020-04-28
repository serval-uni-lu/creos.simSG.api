package duc.sg.java.validator.umatrix;

import duc.sg.java.cycle.detector.InitAllCycleSubs;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.validator.rules.IRule;
import duc.sg.java.validator.rules.Rules;

import java.util.Map;

public class Validator implements  IValidator {
    @Override
    public boolean isValid(Substation substation, Map<Fuse, State> idxColumn) {
        substation.updateAllFuses();
        InitAllCycleSubs.init(substation);


        IRule[] rules = Rules.getRules();

        for(IRule rule: rules) {
            if(!rule.apply(substation, idxColumn)) {
                return false;
            }
        }

        return true;
    }
}
