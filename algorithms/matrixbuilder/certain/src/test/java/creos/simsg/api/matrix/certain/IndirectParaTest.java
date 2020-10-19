package creos.simsg.api.matrix.certain;

import creos.simsg.api.scenarios.IndirectParaSC;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IndirectParaTest extends MatrixBuilderTest {
    @Override
    protected void createSubstation() {
        substation = new ScenarioBuilder()
                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .build()
                .getSubstation();
    }

    private static Arguments[] openCloseF6F10() {
        return TestHelper.generateAllPossibilities(IndirectParaSC.F6_NAME, IndirectParaSC.F10_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6F10")
    public void sc1_allClosed(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,0,0,0,
                1,0,-1,0,0,0,0,0,
                0,1,0,1,0,1,0,0,
                0,0,0,1,1,0,0,0,
                0,0,0,0,0,1,0,0,
                0,0,0,0,1,0,1,1,
                0,0,1,0,0,0,1,0,
                0,0,0,0,0,0,0,1,
        };
        genericTest(expected, toOpen);
    }

}
