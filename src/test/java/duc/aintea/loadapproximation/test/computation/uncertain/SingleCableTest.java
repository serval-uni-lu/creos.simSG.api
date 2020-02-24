package duc.aintea.loadapproximation.test.computation.uncertain;

import duc.aintea.loadapproximation.UncertainLoadApproximator;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Meter;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.SingleCableBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SingleCableTest {
    private Substation substation;
    private Fuse fuse_subs, fuse_cabinet;
    private Cable cable;
    private Meter m1, m2, m3;

    @BeforeEach
    public void init() {
        substation = SingleCableBuilder.build();
        fuse_subs = substation.getFuses().get(0);

        fuse_cabinet = fuse_subs.getOpposite();

        m1 = new Meter("m1");
        m2 = new Meter("m2");
        m3 = new Meter("m3");

        cable = fuse_subs.getCable();
        cable.addMeters(m1, m2, m3);
    }

    @Test
    public void testAllClosed() {
        m1.setConsumption(10);
        m2.setConsumption(10);
        m3.setConsumption(10);

        fuse_subs.openFuse();
        fuse_subs.getStatus().setConfAsProb(0.3);

        fuse_cabinet.getStatus().setConfAsProb(0.5);

        UncertainLoadApproximator.approximate(substation);

        System.out.println("F1");
        for(var f: fuse_subs.getUncertainLoad().entrySet()) {
            System.out.println(f.getKey() + " -> " + f.getValue());
        }

        System.out.println("F2");
        for(var f: fuse_cabinet.getUncertainLoad().entrySet()) {
            System.out.println(f.getKey() + " -> " + f.getValue());
        }

    }



}
