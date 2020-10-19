package duc.sg.java.validator.rules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

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
