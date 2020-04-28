package duc.sg.java.loadapproximator.uncertain.naive.test.computation.uncertain;

import duc.sg.java.loadapproximator.uncertain.naive.UncertainLoadApproximator;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Meter;
import duc.sg.java.model.Substation;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import duc.sg.java.scenarios.SingleCableSC;
import org.junit.jupiter.api.BeforeEach;

public class SingleCableTest {
    private Substation substation;
    private Fuse fuse_subs, fuse_cabinet;
    private Cable cable;
    private Meter m1;

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

        cable = fuse_subs.getCable();
        cable.addMeters(m1);
    }

//    @Test
    public void testAllClosed() {
        m1.setConsumption(20);

        fuse_subs.closeFuse();
        fuse_subs.getStatus().setConfIsClosed(0.3);

        UncertainLoadApproximator.approximate(substation);

//        System.out.println("F1");
//        for(var f: fuse_subs.getUncertainLoad().entrySet()) {
//            System.out.println(f.getKey() + " -> " + f.getValue());
//        }
//
//        System.out.println("F2");
//        for(var f: fuse_cabinet.getUncertainLoad().entrySet()) {
//            System.out.println(f.getKey() + " -> " + f.getValue());
//        }

    }



}
