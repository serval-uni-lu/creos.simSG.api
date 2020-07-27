package duc.sg.java.loadapproximator.loadapproximation;

import duc.sg.java.loadapproximator.LoadApproximator;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Map;

public class CertainApproximator implements LoadApproximator<Double> {
    @Override
    public void approximateAndSave(Substation substation, Map<Fuse, State> configuration) {

    }

    @Override
    public Map<Fuse, Double> getFuseLoads(Substation substation, Map<Fuse, State> configuration) {
        return null;
    }

    @Override
    public Map<Cable, Double> getCableLoads(Substation substation, Map<Fuse, State> configuration) {
        return null;
    }
}
