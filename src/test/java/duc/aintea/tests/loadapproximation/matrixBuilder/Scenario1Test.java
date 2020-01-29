package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Cabinet;
import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Scenario1Test {
    private static Substation subs1;
    private static Fuse f1, f2;


    /*
        subs1-[f1]----(cbl1)----[f2]-c1
     */
    @BeforeEach
    public void init() {
        subs1 = new Substation("subs");
        Cabinet c1 = new Cabinet("cab");

        Cable cbl1 = new Cable();

        f1 = new Fuse("f1_subs");
        f2 = new Fuse("f2_cab");

        subs1.addFuses(f1);
        c1.addFuses(f2);
        cbl1.setFuses(f1, f2);
    }

    private double[] buildMatrix() {
        return new MatrixBuilder().build(subs1);
    }


    @Test
    public void testScenario1_allClose() {
        assertArrayEquals(new double[]{1,0}, buildMatrix());
    }

    @Test
    public void testScenario1_f1Open() {
        f1.openFuse();
        assertArrayEquals(new double[]{0,0}, buildMatrix());
    }

    @Test
    public void testScenario1_f2Open() {
        f2.openFuse();
        assertArrayEquals(new double[]{1,0}, buildMatrix());
    }

    @Test
    public void testScenario1_allOpen() {
        f1.openFuse();
        f2.openFuse();
        assertArrayEquals(new double[]{0,0}, buildMatrix());
    }
}
