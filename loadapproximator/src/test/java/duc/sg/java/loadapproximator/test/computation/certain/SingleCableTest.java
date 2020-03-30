package duc.sg.java.loadapproximator.test.computation.certain;

import duc.sg.java.loadapproximator.loadapproximation.LoadApproximator;
import duc.sg.java.loadapproximator.test.generator.Data;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Meter;
import duc.sg.java.model.Substation;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import duc.sg.java.scenarios.SingleCableSC;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SingleCableTest {
    private Substation substation;
    private Fuse fuse_subs, fuse_cabinet;
    private Cable cable;
    private Meter m1, m2, m3;


    private static Arguments[] threeValues() {
        return Data.generateRandomDoubles(3);
    }


    @BeforeEach
    public void init() {
        substation = new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .build()
                .getGrid()
                .getSubstation(SingleCableSC.SUBSTATION_NAME)
                .get();
        fuse_subs = substation.getFuses().get(0);
        fuse_cabinet = fuse_subs.getOpposite();

        m1 = new Meter("m1");
        m2 = new Meter("m2");
        m3 = new Meter("m3");

        cable = fuse_subs.getCable();
        cable.addMeters(m1, m2, m3);
    }

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testAllClosed(double m1Cons, double m2Cons, double m3Cons) {
        m1.setConsumption(m1Cons);
        m2.setConsumption(m2Cons);
        m3.setConsumption(m3Cons);

        LoadApproximator.approximate(substation);


        MultDblePossibilities cableULoad = cable.getUncertainLoad();
        PossibilityDouble cableLoad = cableULoad.iterator().next();

        MultDblePossibilities fuseLoad = fuse_subs.getUncertainLoad();
        PossibilityDouble fuseSubsLoad = fuseLoad.iterator().next();

        MultDblePossibilities fuseCabULoad = fuse_cabinet.getUncertainLoad();
        PossibilityDouble fuseCabLoad = fuseCabULoad.iterator().next();

        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, fuseSubsLoad.getValue(), 0.001);
        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, cableLoad.getValue(), 0.001);
        Assertions.assertEquals(0, fuseCabLoad.getValue(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testFSubsOpen(double m1Cons, double m2Cons, double m3Cons) {
        m1.setConsumption(m1Cons);
        m2.setConsumption(m2Cons);
        m3.setConsumption(m3Cons);


        fuse_subs.openFuse();

        LoadApproximator.approximate(substation);

        MultDblePossibilities cableULoad = cable.getUncertainLoad();
        PossibilityDouble cableLoad = cableULoad.iterator().next();

        MultDblePossibilities fuseLoad = fuse_subs.getUncertainLoad();
        PossibilityDouble fuseSubsLoad = fuseLoad.iterator().next();

        MultDblePossibilities fuseCabULoad = fuse_cabinet.getUncertainLoad();
        PossibilityDouble fuseCabLoad = fuseCabULoad.iterator().next();

        Assertions.assertEquals(0, cableLoad.getValue(), 0.001);
        Assertions.assertEquals(0, fuseSubsLoad.getValue(), 0.001);
        Assertions.assertEquals(0, fuseCabLoad.getValue(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testFCabOpen(double m1Cons, double m2Cons, double m3Cons) {
        fuse_cabinet.openFuse();

        m1.setConsumption(m1Cons);
        m2.setConsumption(m2Cons);
        m3.setConsumption(m3Cons);

        LoadApproximator.approximate(substation);

        MultDblePossibilities cableULoad = cable.getUncertainLoad();
        PossibilityDouble cableLoad = cableULoad.iterator().next();

        MultDblePossibilities fuseLoad = fuse_subs.getUncertainLoad();
        PossibilityDouble fuseSubsLoad = fuseLoad.iterator().next();

        MultDblePossibilities fuseCabULoad = fuse_cabinet.getUncertainLoad();
        PossibilityDouble fuseCabLoad = fuseCabULoad.iterator().next();

        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, cableLoad.getValue(), 0.001);
        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, fuseSubsLoad.getValue(), 0.001);
        Assertions.assertEquals(0, fuseCabLoad.getValue(), 0.001);
    }


    @ParameterizedTest
    @MethodSource("threeValues")
    public void testAllOpen(double m1Cons, double m2Cons, double m3Cons) {
        m1.setConsumption(m1Cons);
        m2.setConsumption(m2Cons);
        m3.setConsumption(m3Cons);

        fuse_subs.openFuse();
        fuse_cabinet.openFuse();

        LoadApproximator.approximate(substation);

        MultDblePossibilities cableULoad = cable.getUncertainLoad();
        PossibilityDouble cableLoad = cableULoad.iterator().next();

        MultDblePossibilities fuseLoad = fuse_subs.getUncertainLoad();
        PossibilityDouble fuseSubsLoad = fuseLoad.iterator().next();

        MultDblePossibilities fuseCabULoad = fuse_cabinet.getUncertainLoad();
        PossibilityDouble fuseCabLoad = fuseCabULoad.iterator().next();

        Assertions.assertEquals(0, cableLoad.getValue(), 0.001);
        Assertions.assertEquals(0, fuseSubsLoad.getValue(), 0.001);
        Assertions.assertEquals(0, fuseCabLoad.getValue(), 0.001);
    }
}
