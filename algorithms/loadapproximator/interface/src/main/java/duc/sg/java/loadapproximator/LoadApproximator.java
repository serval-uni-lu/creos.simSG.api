package duc.sg.java.loadapproximator;

import duc.sg.java.extracter.EffectiveConfigurationExtracter;
import duc.sg.java.model.*;

import java.util.Map;

public interface LoadApproximator<T> {
    void approximateAndSave(Substation substation, Configuration configuration);
    default void approximateAndSave(Substation substation) {
        Configuration configuration = EffectiveConfigurationExtracter.INSTANCE
                .getExtracted(substation)
                .get(0);
        approximateAndSave(substation, configuration);
    }

    Map<Fuse, T> getFuseLoads(Substation substation, Configuration configuration);
    default Map<Fuse, T> getFuseLoads(Substation substation) {
        Configuration configuration = EffectiveConfigurationExtracter.INSTANCE
                .getExtracted(substation)
                .get(0);
        return getFuseLoads(substation, configuration);
    }

    Map<Cable, T> getCableLoads(Substation substation, Configuration configuration);
    default Map<Cable, T> getCableLoads(Substation substation) {
        Configuration configuration = EffectiveConfigurationExtracter.INSTANCE
                .getExtracted(substation)
                .get(0);
        return getCableLoads(substation, configuration);
    }
}
