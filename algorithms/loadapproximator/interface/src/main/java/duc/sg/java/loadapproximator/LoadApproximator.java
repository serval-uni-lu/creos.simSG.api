package duc.sg.java.loadapproximator;

import duc.sg.java.model.*;

import java.util.Map;

public interface LoadApproximator<T> {
    void approximateAndSave(Substation substation, Map<Fuse, State> configuration);
    default void approximateAndSave(Substation substation) {
        Map<Fuse, State> configuration = ApproximatorUtils.extractEffectiveConfiguration(substation);
        approximateAndSave(substation, configuration);
    }

    Map<Fuse, T> getFuseLoads(Substation substation, Map<Fuse, State> configuration);
    default Map<Fuse, T> getFuseLoads(Substation substation) {
        Map<Fuse, State> configuration = ApproximatorUtils.extractEffectiveConfiguration(substation);
        return getFuseLoads(substation, configuration);
    }

    Map<Cable, T> getCableLoads(Substation substation, Map<Fuse, State> configuration);
    default Map<Cable, T> getCableLoads(Substation substation) {
        Map<Fuse, State> configuration = ApproximatorUtils.extractEffectiveConfiguration(substation);
        return getCableLoads(substation, configuration);
    }
}
