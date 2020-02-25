package duc.aintea.loadapproximation.test.computation.certain;

import duc.aintea.loadapproximation.LoadApproximator;
import duc.aintea.loadapproximation.test.generator.Data;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Meter;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.SingleCableBuilder;
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
        substation = SingleCableBuilder.build();
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

        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, cable.getLoad(), 0.001);
        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, fuse_subs.getLoad(), 0.001);
        Assertions.assertEquals(0, fuse_cabinet.getLoad(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testFSubsOpen(double m1Cons, double m2Cons, double m3Cons) {
        m1.setConsumption(m1Cons);
        m2.setConsumption(m2Cons);
        m3.setConsumption(m3Cons);


        fuse_subs.openFuse();

        LoadApproximator.approximate(substation);

        Assertions.assertEquals(0, cable.getLoad(), 0.001);
        Assertions.assertEquals(0, fuse_subs.getLoad(), 0.001);
        Assertions.assertEquals(0, fuse_cabinet.getLoad(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testFCabOpen(double m1Cons, double m2Cons, double m3Cons) {
        fuse_cabinet.openFuse();

        m1.setConsumption(m1Cons);
        m2.setConsumption(m2Cons);
        m3.setConsumption(m3Cons);

        LoadApproximator.approximate(substation);

        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, cable.getLoad(), 0.001);
        Assertions.assertEquals(m1Cons + m2Cons + m3Cons, fuse_subs.getLoad(), 0.001);
        Assertions.assertEquals(0, fuse_cabinet.getLoad(), 0.001);
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

        Assertions.assertEquals(0, cable.getLoad(), 0.001);
        Assertions.assertEquals(0, fuse_subs.getLoad(), 0.001);
        Assertions.assertEquals(0, fuse_cabinet.getLoad(), 0.001);
    }
}
