package duc.aintea.loadapproximation.test.matrixBuilder.certain;

import duc.aintea.loadapproximation.test.LoadTest;
import duc.aintea.loadapproximation.matrix.FuseStatesMatrix;
import duc.aintea.loadapproximation.matrix.MatrixBuilder;
import org.junit.jupiter.api.Assertions;

public abstract class MatrixBuilderTest extends LoadTest {

    protected void genericTest(double[] expected, String... toOpen) {
        openFuses(toOpen);
        FuseStatesMatrix matrix = MatrixBuilder.build(substation);
        Assertions.assertArrayEquals(expected,  matrix.getData());
    }
}
