package duc.sg.java.loadapproximator.certain.tests;

import duc.sg.java.model.Substation;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.sg.java.scenarios.IndirectParaSC.*;


public class IndirectParaTest extends LoadApproximatorTest {
    @Override
    protected Substation createSubstation() {
        return new ScenarioBuilder()
                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .build()
                .getSubstation();
    }

    @Override
    protected String[] getFuses() {
        return new String[]{F1_NAME, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME, F7_NAME, F8_NAME, F9_NAME};
    }

    @Override
    protected String[] getFuseCables() {
        return new String[]{F1_NAME, F3_NAME, F5_NAME, F7_NAME, F9_NAME};
    }

    private static Arguments[] openCloseF6F10() {
        return TestHelper.generateAllPossibilitiesWithValues(5, F6_NAME, F10_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6F10")
    public void sc1_allClosed(String[] toOPen, Double[] consumptions) {
        var half = 0.;
        for (var d: consumptions) {
            half+=d;
        }
        half = half / 2;

        var expectedFuses = new double[] {
                half,
                consumptions[0] - half,
                half,
                consumptions[1] - half,
                consumptions[2],
                0,
                half - consumptions[1] - consumptions[2],
                consumptions[1] + consumptions[2] + consumptions[3] - half,
                consumptions[4],
                0
        };

        var expectedCables = new double[] {
                half,
                half,
                Math.max(expectedFuses[4], expectedFuses[5]),
                Math.max(expectedFuses[6], expectedFuses[7]),
                consumptions[4]

        };

        genericTest(toOPen, consumptions, expectedCables, expectedFuses);
    }

}
