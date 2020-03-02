package duc.aintea.benchmark.loadapproximator.singleCable;

import duc.aintea.benchmark.loadapproximator.GenericBench;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.SingleCableBuilder;

public abstract class SingleCableBench extends GenericBench  {

    @Override
    protected Substation initSubs() {
        return SingleCableBuilder.build();
    }

    @Override
    protected Fuse[] initFuses() {
        return SingleCableBuilder.extractFuses(substation);
    }

    @Override
    protected Cable[] getCables() {
        return SingleCableBuilder.extractCables(substation);
    }


}
