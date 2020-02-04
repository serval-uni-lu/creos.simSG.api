package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.sg.scenarios.IndirectPara;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.tests.sg.scenarios.IndirectPara.*;

public class IndirectParaTest extends MatrixBuilderTest {
    @Override
    protected void createSubstation() {
        substation = IndirectPara.build(1);
    }

    private static Arguments[] openCloseF6F10() {
        return Utils.generator(F6_NAME, F10_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6F10")
    public void sc1_allClosed(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,0,0,0,
                0,0,0,1,1,0,0,0,
                0,0,0,0,0,1,0,0,
                0,0,1,0,0,0,1,0,
                0,0,0,0,0,0,0,1,
                0,1,0,1,0,1,0,0,
                0,0,0,0,1,0,1,1,
                1,0,-1,0,0,0,0,0,
        };
        genericTest(expected, toOpen);
    }

}
