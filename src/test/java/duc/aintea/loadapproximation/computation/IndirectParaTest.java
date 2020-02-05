package duc.aintea.loadapproximation.computation;

import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.sg.scenarios.IndirectPara;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.sg.scenarios.IndirectPara.*;

public class IndirectParaTest extends LoadApproximatorTest {
    @Override
    protected void createSubstation() {
        substation = IndirectPara.build();
    }

    private static Arguments[] openCloseF6F10() {
        return Data.generateAllPossibilitiesWithValues(5, IndirectPara.F6_NAME, IndirectPara.F10_NAME);
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

        var fuseCables = new String[]{F1_NAME, F3_NAME, F5_NAME, F7_NAME, F9_NAME};
        var fuses = new String[]{F1_NAME, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME, F7_NAME, F8_NAME, F9_NAME};
        genericTest(toOPen, consumptions, expectedCables, expectedFuses, fuseCables, fuses);
    }

}
