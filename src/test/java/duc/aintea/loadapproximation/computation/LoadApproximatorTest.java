package duc.aintea.loadapproximation.computation;

import duc.aintea.loadapproximation.LoadTest;
import duc.aintea.sg.Meter;

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


}
