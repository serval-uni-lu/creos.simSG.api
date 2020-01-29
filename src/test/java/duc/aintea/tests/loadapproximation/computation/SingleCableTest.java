package duc.aintea.tests.loadapproximation.computation;

import duc.aintea.tests.loadapproximation.LoadApproximation;
import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.sg.scenarios.SingleCableBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SingleCableTest {
    private Substation substation;
    private Fuse fuse_subs, fuse_cabinet;

    @BeforeEach
    public void init() {
        substation = SingleCableBuilder.build(0);
        fuse_subs = substation.getFuses().get(0);
        fuse_cabinet = fuse_subs.getOpposite();
    }

    @ParameterizedTest
    @MethodSource("duc.aintea.tests.loadapproximation.DataGenerator#oneValue")
    public void testAllClosed(double consumption) {
        double[] matrix = new MatrixBuilder().build(substation);
        var approximator = new LoadApproximation(new double[]{consumption, 0.}, matrix);
        double[] loads = approximator.computeLoad();

        Assertions.assertArrayEquals(new double[]{consumption, 0}, loads, 0.1);
    }

    @Test
    public void testFSubsOpen() {
        fuse_subs.openFuse();

        double[] matrix = new MatrixBuilder().build(substation);
        var approximator = new LoadApproximation(new double[]{0.}, matrix);
        double[] loads = approximator.computeLoad();

        Assertions.assertArrayEquals(new double[]{0}, loads, 0.1);
    }

    @ParameterizedTest
    @MethodSource("duc.aintea.tests.loadapproximation.DataGenerator#oneValue")
    public void testFCabOpen(double consumption) {
        fuse_cabinet.openFuse();

        double[] matrix = new MatrixBuilder().build(substation);
        var approximator = new LoadApproximation(new double[]{consumption, 0.}, matrix);
        double[] loads = approximator.computeLoad();

        Assertions.assertArrayEquals(new double[]{consumption, 0}, loads, 0.1);
    }


    @Test
    public void testAllOpen() {
        fuse_subs.openFuse();
        fuse_cabinet.openFuse();

        double[] matrix = new MatrixBuilder().build(substation);
        var approximator = new LoadApproximation(new double[]{0.}, matrix);
        double[] loads = approximator.computeLoad();

        Assertions.assertArrayEquals(new double[]{0}, loads, 0.1);
    }
}
