package duc.sg.java.validator.umatrix;

import duc.sg.java.cycle.all.InitAllCycleSubs2;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.validator.rules.IRule;
import duc.sg.java.validator.rules.Rules;

import java.util.Map;

public class GridValidator implements  IValidator {
    @Override
    public boolean isValid(Substation substation, Map<Fuse, State> idxColumn) {
        substation.updateAllFuses();
        InitAllCycleSubs2.init(substation);


        IRule[] rules = Rules.getAllRules();

        for(IRule rule: rules) {
            if(!rule.apply(substation, idxColumn)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isValid(Fuse[] setOfFuses, Map<Fuse, State> idxColumn) {
        return false;
    }
}
