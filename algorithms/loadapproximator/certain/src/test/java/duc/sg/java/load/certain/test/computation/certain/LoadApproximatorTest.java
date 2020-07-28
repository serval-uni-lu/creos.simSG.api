package duc.sg.java.load.certain.test.computation.certain;

import duc.sg.java.loadapproximator.loadapproximation.LoadApproximator;
import duc.sg.java.load.certain.test.LoadTest;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Meter;

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
            Cable cable = fusesMap.get(fuseCables[i]).getCable();
//            MultDblePossibilities cableULoad = cable.getUncertainLoad();
//            PossibilityDouble cableLoad = cableULoad.iterator().next();
//            assertEquals(expectedCables[i], cableLoad.getValue(), DELTA, "Error for cable " + (i+1));
        }

        for (int i = 0; i < fuses.length; i++) {
            Fuse fuse = fusesMap.get(fuses[i]);
//            MultDblePossibilities fuseULoad = fuse.getUncertainLoad();
//            PossibilityDouble fuseLoad = fuseULoad.iterator().next();
//            assertEquals(expectedFuses[i], fuseLoad.getValue(), DELTA, "Error for fuse " + (i+1));
        }
    }

    protected abstract String[] getFuses();
    protected abstract String[] getFuseCables();


}
