package duc.sg.java.loadapproximator;

import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ApproximatorUtils {

    static Map<Fuse, State> extractEffectiveConfiguration(Substation substation) {
        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(substation);
        Map<Fuse, State> configuration = new HashMap<>();
        for(Fuse f: allFuses) {
            configuration.put(f, f.getStatus().getState());
        }
        return configuration;
    }
}