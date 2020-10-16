package duc.sg.java.loadapproximator.certain.tests;

import duc.sg.java.extractor.FuseExtractor;
import duc.sg.java.loadapproximator.certain.CertainApproximator;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Meter;
import duc.sg.java.model.Substation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class LoadApproximatorTest {
    protected static final double DELTA = 0.001;
    protected Substation substation;
    protected Map<String, Fuse> fusesMap;


    @BeforeEach
    public void init() {
        substation = createSubstation();
        initFuseMap();
    }

    protected abstract Substation createSubstation();

    protected void initFuseMap() {
        fusesMap = new HashMap<>();

        FuseExtractor.INSTANCE
                .getExtracted(substation)
                .forEach((Fuse f) -> fusesMap.put(f.getName(), f));
    }

    protected void openFuses(String[] toOpen) {
        for (var fName: toOpen) {
            fusesMap.get(fName).openFuse();
        }
    }


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
        initConsumptions(consumptions, fuseCables);

        openFuses(toOpen);
        Map<Cable, Double> cableLoads = CertainApproximator.INSTANCE.getCableLoads(substation);
        for (int i = 0; i < fuseCables.length; i++) {
            Cable cable = fusesMap.get(fuseCables[i]).getCable();
            Assertions.assertEquals(expectedCables[i], cableLoads.get(cable), DELTA, "Error for cable " + (i+1));
        }

        Map<Fuse, Double> fuseLoads = CertainApproximator.INSTANCE.getFuseLoads(substation);
        for (int i = 0; i < fuses.length; i++) {
            Fuse fuse = fusesMap.get(fuses[i]);
            assertEquals(expectedFuses[i], fuseLoads.get(fuse), DELTA, "Error for fuse " + (i + 1));
        }
    }

    protected abstract String[] getFuses();
    protected abstract String[] getFuseCables();


}
