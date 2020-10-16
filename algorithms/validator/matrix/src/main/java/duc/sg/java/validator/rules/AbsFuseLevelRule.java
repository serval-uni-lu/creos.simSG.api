package duc.sg.java.validator.rules;

import duc.sg.java.extractor.FuseExtractor;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Collection;
import java.util.Map;

abstract class AbsFuseLevelRule implements IRule {
    @Override
    public final boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        Collection<Fuse> fuses = FuseExtractor.INSTANCE.getExtracted(substation);

        for(Fuse fuse: fuses) {
            if(!apply(fuse, fuseStateMap)) {
                return false;
            }
        }

        return true;

    }


    public abstract boolean apply(Fuse fuse, Map<Fuse, State> fuseStateMap);

}
