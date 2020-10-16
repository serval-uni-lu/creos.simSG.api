package duc.sg.java.loadapproximator;

import duc.sg.java.extracter.EffectiveConfigurationExtractor;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.Map;

public interface LoadApproximator<T> {
    Map<Fuse, T> approximate(Substation substation, Configuration configuration);
    void approximateAndSave(Substation substation, Configuration configuration);
    default void approximateAndSave(Substation substation) {
        Configuration configuration = EffectiveConfigurationExtractor.INSTANCE
                .getExtracted(substation)
                .get(0);
        approximateAndSave(substation, configuration);
    }

    Map<Fuse, T> getFuseLoads(Substation substation, Configuration configuration, boolean forceRecompute);
    default Map<Fuse, T> getFuseLoads(Substation substation) {
        return getFuseLoads(substation, false);
    }
    default Map<Fuse, T> getFuseLoads(Substation substation, boolean forRecompute) {
        Configuration configuration = EffectiveConfigurationExtractor.INSTANCE
                .getExtracted(substation)
                .get(0);
        return getFuseLoads(substation, configuration, forRecompute);
    }
    default Map<Fuse, T> getFuseLoads(Substation substation, Configuration configuration) {
        return getFuseLoads(substation, configuration, false);
    }

    Map<Cable, T> getCableLoads(Substation substation, Configuration configuration, boolean forceRecompute);
    default Map<Cable, T> getCableLoads(Substation substation, Configuration configuration) {
        return getCableLoads(substation, configuration, false);
    }
    default Map<Cable, T> getCableLoads(Substation substation) {
        return getCableLoads(substation, false);
    }
    default Map<Cable, T> getCableLoads(Substation substation, boolean forceRecompute) {
        Configuration configuration = EffectiveConfigurationExtractor.INSTANCE
                .getExtracted(substation)
                .get(0);
        return getCableLoads(substation, configuration, forceRecompute);
    }
}
