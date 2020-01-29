package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.sg.scenarios.SingleCableBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SingleCableTest {
    private static Substation substation;
    private static Fuse fuse_subs, fuse_cab;


    /*
        subs1-[f1]----(cbl1)----[f2]-c1
     */
    @BeforeEach
    public void init() {
        substation = SingleCableBuilder.build(0);
        fuse_subs = substation.getFuses().get(0);
        fuse_cab = fuse_subs.getOpposite();
    }

    private double[] buildMatrix() {
        return new MatrixBuilder().build(substation);
    }


    @Test
    public void testScenario1_allClose() {
        assertArrayEquals(new double[]{1}, buildMatrix());
    }

    @Test
    public void testScenario1_f1Open() {
        fuse_subs.openFuse();
        assertArrayEquals(new double[]{0}, buildMatrix());
    }

    @Test
    public void testScenario1_f2Open() {
        fuse_cab.openFuse();
        assertArrayEquals(new double[]{1}, buildMatrix());
    }

    @Test
    public void testScenario1_allOpen() {
        fuse_subs.openFuse();
        fuse_cab.openFuse();
        assertArrayEquals(new double[]{0}, buildMatrix());
    }
}
