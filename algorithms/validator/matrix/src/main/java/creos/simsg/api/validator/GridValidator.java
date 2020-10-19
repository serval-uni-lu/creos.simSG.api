package creos.simsg.api.validator;

import creos.simsg.api.circlefinder.CircleFinder;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;
import creos.simsg.api.validator.rules.IRule;
import creos.simsg.api.validator.rules.Rules;

import java.util.Map;

/**
 * Apply all rules to validate the configuration of a topology
 */
public class GridValidator implements  IValidator {
    @Override
    public boolean isValid(Substation substation, Map<Fuse, State> idxColumn, IRule... iRules){
        CircleFinder.getDefault().findAndSave(substation);
        IRule[] rules;
        if(iRules.length < 1) {
            rules = Rules.getAllRules();
        } else {
            rules = iRules;
        }

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
