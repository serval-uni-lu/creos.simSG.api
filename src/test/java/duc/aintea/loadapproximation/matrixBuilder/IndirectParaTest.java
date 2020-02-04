package duc.aintea.loadapproximation.matrixBuilder;

import duc.aintea.sg.scenarios.IndirectPara;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IndirectParaTest extends MatrixBuilderTest {
    @Override
    protected void createSubstation() {
        substation = IndirectPara.build(1);
    }

    private static Arguments[] openCloseF6F10() {
        return Utils.generator(IndirectPara.F6_NAME, IndirectPara.F10_NAME);
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
