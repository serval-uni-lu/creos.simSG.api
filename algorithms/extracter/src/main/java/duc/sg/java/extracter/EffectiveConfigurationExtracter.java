package duc.sg.java.extracter;

import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.*;

public class EffectiveConfigurationExtracter implements Extracter<Configuration> {
    public static final EffectiveConfigurationExtracter INSTANCE = new EffectiveConfigurationExtracter();

    private EffectiveConfigurationExtracter(){}

    @Override
    public void extractAndSave(Substation substation) {
        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(substation);
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
