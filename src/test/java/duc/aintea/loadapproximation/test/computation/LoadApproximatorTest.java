package duc.aintea.loadapproximation.test.computation;

import duc.aintea.loadapproximation.LoadApproximator;
import duc.aintea.loadapproximation.test.LoadTest;
import duc.aintea.sg.Meter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class LoadApproximatorTest extends LoadTest {
    protected static final double DELTA = 0.001;


    protected void initConsumptions(Double[] values, String... startingFuseName) {
        for (int i = 0; i < startingFuseName.length; i++) {
            var fuse = fusesMap.get(startingFuseName[i]);
            var meter = new Meter("meter_cable_" + startingFuseName[i]);
            meter.setConsumption(values[i]);
            fuse.getCable().addMeters(meter);
        }
    }

    protected void genericTest(String[] toOpen, Double[] consumptions, double[] expectedCables, double[] expectedFuses) {
        var fuseCables = getFuseCables();
        var fuses = getFuses();
        openFuses(toOpen);
        initConsumptions(consumptions, fuseCables);
        LoadApproximator.approximate(substation);

        for (int i = 0; i < fuseCables.length; i++) {
            var fuse = fusesMap.get(fuseCables[i]);
            assertEquals(expectedCables[i], fuse.getCable().getLoad(), DELTA, "Error for cable " + (i+1));
        }

        for (int i = 0; i < fuses.length; i++) {
            var fuse = fusesMap.get(fuses[i]);
            assertEquals(expectedFuses[i], fuse.getLoad(), DELTA, "Error for fuse " + (i+1));
        }
    }

    protected abstract String[] getFuses();
    protected abstract String[] getFuseCables();


}
