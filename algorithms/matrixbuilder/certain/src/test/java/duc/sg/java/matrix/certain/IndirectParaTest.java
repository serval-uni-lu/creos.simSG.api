package duc.sg.java.matrix.certain;

import duc.sg.java.scenarios.IndirectParaSC;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IndirectParaTest extends MatrixBuilderTest {
    @Override
    protected void createSubstation() {
        substation = new ScenarioBuilder()
                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .build()
                .getGrid()
                .getSubstation(IndirectParaSC.SUBSTATION_NAME)
                .get();
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
