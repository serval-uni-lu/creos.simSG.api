package duc.aintea.benchmark.loadapproximator.cabinet;

import duc.aintea.benchmark.loadapproximator.GenericBench;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.CabinetBuilder;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public abstract class CabinetBench extends GenericBench {

    @Override
    protected Substation initSubs() {
        return CabinetBuilder.build();
    }

    @Override
    protected Fuse[] initFuses() {
        return CabinetBuilder.extractFuses(substation);
    }

    @Override
    protected Cable[] getCables() {
        return CabinetBuilder.extractCables(substation);
    }
}
