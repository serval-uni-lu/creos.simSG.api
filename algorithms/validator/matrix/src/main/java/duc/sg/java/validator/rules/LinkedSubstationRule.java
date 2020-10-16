package duc.sg.java.validator.rules;

import duc.sg.java.extractor.CableExtracter;
import duc.sg.java.extractor.EffectiveConfigurationExtracter;
import duc.sg.java.extractor.EntityExtracter;
import duc.sg.java.extractor.Extracter;
import duc.sg.java.model.*;

import java.util.List;
import java.util.Map;

/**
 * Class that hold the business rule concerning the grid topology : Power flow can not go from substation to another
 */
public class LinkedSubstationRule implements IRule {

    @Override
    public boolean apply(Substation substation, Map<Fuse, State> fuseStateMap) {
        SmartGrid full_grid = substation.getGrid();
        Substation initial_substation = substation;
        //Compute the grid subgraph i.e. the subgrid that represent all connected (fuses closed) entities
//        List<Cable> cables = CableExtracter.INSTANCE.getExtracted(initial_substation);
        List<Entity> entities = EntityExtracter.INSTANCE.getExtracted(initial_substation);
        int n_substations = 0;
        for(Entity e:entities){
            if(e instanceof Substation)
                n_substations++;
        }

        if(n_substations < 2)
            return true;


        List<Configuration> c = EffectiveConfigurationExtracter.INSTANCE.getExtracted(initial_substation);
        return true;
    }

}
