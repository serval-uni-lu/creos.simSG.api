package creos.simsg.api.validator.rules;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;

import java.util.Map;

/**
 * Interface class that should be implemented by all rules
 */
public interface IRule {
    boolean apply(Substation substation, Map<Fuse, State> fuseStateMap);
    default boolean apply(Fuse[] fuses, Map<Fuse, State> fuseStateMap){
        return true;
    }
}
