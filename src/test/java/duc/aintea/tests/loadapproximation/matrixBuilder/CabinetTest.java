package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.sg.scenarios.CabinetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CabinetTest {
    private static Substation substation;

    @BeforeEach
    public void init() {
        substation = CabinetBuilder.build(0);
    }

    private double[] buildMatrix() {
        return new MatrixBuilder().build(substation);
    }

    @Test
    public void testAllCLosed() {
        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1,
        };
        Assertions.assertArrayEquals(expected, buildMatrix(), 0.1);
    }
}
