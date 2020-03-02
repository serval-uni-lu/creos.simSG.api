package duc.aintea.benchmark.loadapproximator.paraCabinet;

import duc.aintea.benchmark.loadapproximator.GenericBench;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.ParaCabinetBuilder;

public abstract class ParaCabinetBench extends GenericBench {

    @Override
    protected Substation initSubs() {
        return ParaCabinetBuilder.build();
    }

    @Override
    protected Fuse[] initFuses() {
        return ParaCabinetBuilder.extractFuses(substation);
    }

    @Override
    protected Cable[] getCables() {
        return ParaCabinetBuilder.extractCables(substation);
    }
}
