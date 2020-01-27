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
    private static Substation substation;
    private static Fuse[] fuses;

    @BeforeEach
    public void init() {
        substation = new Substation("subs");
        fuses = new Fuse[2];

        Fuse f1 = new Fuse("f1_subs");
        substation.addFuses(f1);
        Fuse f2 = new Fuse("f2_cab");
        fuses[0] = f1;
        fuses[1] = f2;

        Cable cable = new Cable();
        cable.setFirstFuse(f1);
        cable.setSecondFuse(f2);

        Cabinet cabinet = new Cabinet("cab");
        cabinet.addFuses(f2);
    }


    @Test
    public void scenario1_allClose() {
        MatrixBuilder matrixBuilder = new MatrixBuilder();
        double[] matrix = matrixBuilder.build(substation);

        assertArrayEquals(new double[]{1,1,0,1}, matrix);
    }

    @Test
    public void scenario1_allOpen() {
        fuses[0].openFuse();
        fuses[1].openFuse();

        MatrixBuilder matrixBuilder = new MatrixBuilder();
        double[] matrix = matrixBuilder.build(substation);

        assertArrayEquals(new double[]{}, matrix);
    }

    @Test
    public void scenario1_f1Open() {
        fuses[0].openFuse();

        MatrixBuilder matrixBuilder = new MatrixBuilder();
        double[] matrix = matrixBuilder.build(substation);

        assertArrayEquals(new double[]{1,0}, matrix);
    }
}
