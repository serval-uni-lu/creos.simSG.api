package creos.simsg.api.extractor;

import creos.simsg.api.model.Configuration;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;

import java.util.*;

/**
 * Extract the effective configuration (fuse states) of the grid
 */
public class EffectiveConfigurationExtractor implements Extractor<Configuration> {
    public static final EffectiveConfigurationExtractor INSTANCE = new EffectiveConfigurationExtractor();

    private EffectiveConfigurationExtractor(){}

    @Override
    public void extractAndSave(Substation substation) {
        List<Fuse> allFuses = FuseExtractor.INSTANCE.getExtracted(substation);
        Map<Fuse, State> configuration = new HashMap<>();
        for(Fuse f: allFuses) {
            configuration.put(f, f.getStatus().getState());
        }

        var res = new ArrayList<Configuration>(1);
        res.add(new Configuration(configuration));
        substation.getGrid()
                .save(ExtracterUtils.getKeyConf(substation), res);
    }

    @Override
    public List<Configuration> getExtracted(Substation substation) {
        String key = ExtracterUtils.getKeyConf(substation);
        Optional<Object> optCOnf = substation.getGrid().retrieve(key);
        if(optCOnf.isEmpty()) {
            extractAndSave(substation);
            optCOnf = substation.getGrid().retrieve(key);
        }
        return (List<Configuration>) optCOnf.get();
    }
}
