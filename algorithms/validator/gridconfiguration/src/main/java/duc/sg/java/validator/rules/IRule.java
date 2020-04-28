package duc.sg.java.validator.rules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Map;

public interface IRule {
    boolean apply(Substation substation, Map<Fuse, State> fuseStateMap);
}
