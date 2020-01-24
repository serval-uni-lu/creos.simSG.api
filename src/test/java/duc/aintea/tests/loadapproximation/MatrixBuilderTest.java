package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixBuilderTest {



    @Test
    public void scenario1_allClose() {
        Substation substation = new Substation("subs");

        Fuse f1 = new Fuse("f1_subs");
        substation.addFuses(f1);

        Fuse f2 = new Fuse("f2_cab");

        Cable cable = new Cable();
        cable.setFirstFuse(f1);
        cable.setSecondFuse(f2);

        Cabinet cabinet = new Cabinet("cab");
        cabinet.addFuses(f2);

        MatrixBuilder matrixBuilder = new MatrixBuilder();
        double[] matrix = matrixBuilder.build(substation);

        assertNotNull(matrix);
        assertEquals(4, matrix.length);

        assertArrayEquals(new double[]{1,1,0,1}, matrix);
    }
}
