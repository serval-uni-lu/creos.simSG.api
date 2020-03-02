package duc.aintea.benchmark.loadapproximator.paraTransformer;

import duc.aintea.benchmark.loadapproximator.GenericBench;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.ParaTransformerBuilder;

public abstract class ParaTransformerBench extends GenericBench {

    @Override
    protected Substation initSubs() {
        return ParaTransformerBuilder.build();
    }

    @Override
    protected Fuse[] initFuses() {
        return ParaTransformerBuilder.extractFuses(substation);
    }

    @Override
    protected Cable[] getCables() {
        return ParaTransformerBuilder.extractCables(substation);
    }
}
