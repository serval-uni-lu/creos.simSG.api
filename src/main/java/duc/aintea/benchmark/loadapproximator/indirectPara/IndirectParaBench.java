package duc.aintea.benchmark.loadapproximator.indirectPara;

import duc.aintea.benchmark.loadapproximator.GenericBench;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.IndirectParaBuilder;

public abstract class IndirectParaBench extends GenericBench {
    @Override
    protected Substation initSubs() {
        return IndirectParaBuilder.build();
    }

    @Override
    protected Fuse[] initFuses() {
        return IndirectParaBuilder.extractFuses(substation);
    }

    @Override
    protected Cable[] getCables() {
        return IndirectParaBuilder.extractCables(substation);
    }
}
