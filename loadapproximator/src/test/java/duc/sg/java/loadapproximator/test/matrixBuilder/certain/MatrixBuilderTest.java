package duc.sg.java.loadapproximator.test.matrixBuilder.certain;

import duc.sg.java.loadapproximator.test.LoadTest;
import duc.sg.java.loadapproximator.loadapproximation.matrix.FuseStatesMatrix;
import duc.sg.java.loadapproximator.loadapproximation.matrix.MatrixBuilder;
import org.junit.jupiter.api.Assertions;

public abstract class MatrixBuilderTest extends LoadTest {

    protected void genericTest(double[] expected, String... toOpen) {
        openFuses(toOpen);
        FuseStatesMatrix matrix = MatrixBuilder.build(substation);
        Assertions.assertArrayEquals(expected,  matrix.getData());
    }
}
