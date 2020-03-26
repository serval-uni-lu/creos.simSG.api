package duc.sg.java.loadapproximator.test.matrixBuilder.certain;

import duc.sg.java.loadapproximator.test.generator.Data;
import duc.aintea.sg.scenarios.IndirectParaBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IndirectParaTest extends MatrixBuilderTest {
    @Override
    protected void createSubstation() {
        substation = IndirectParaBuilder.build();
    }

    private static Arguments[] openCloseF6F10() {
        return Data.generateAllPossibilities(IndirectParaBuilder.F6_NAME, IndirectParaBuilder.F10_NAME);
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
