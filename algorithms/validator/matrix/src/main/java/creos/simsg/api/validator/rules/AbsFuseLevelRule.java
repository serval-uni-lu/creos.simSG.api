package creos.simsg.api.validator.rules;

import creos.simsg.api.extractor.FuseExtractor;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;

import java.util.Collection;
import java.util.Map;

/**
 * Rules that are applied only at the fuse level
 */
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
