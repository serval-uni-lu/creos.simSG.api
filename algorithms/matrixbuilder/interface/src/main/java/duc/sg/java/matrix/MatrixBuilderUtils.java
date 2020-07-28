package duc.sg.java.matrix;

import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixBuilderUtils {
    /**
     *
     * Copy of ApproximatorUtils.extractEffectiveConfiguration
     * TODO: think about a better design
     *
     * @param substation
     * @return
     */
    static Configuration extractEffectiveConfiguration(Substation substation) {
        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(substation);
        Map<Fuse, State> configuration = new HashMap<>();
        for(Fuse f: allFuses) {
            configuration.put(f, f.getStatus().getState());
        }
        return new Configuration(configuration);
    }
}
