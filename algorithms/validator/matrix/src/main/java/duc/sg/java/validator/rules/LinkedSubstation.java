package duc.sg.java.validator.rules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Map;

/**
 * Class that hold the business rule concerning the grid topology : Power flow can not go from substation to another
 */
public class LinkedSubstation implements IRule {
    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        return true;
    }
}
