package duc.aintea.loadapproximation.computation;

import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.loadapproximation.generator.Utils;
import duc.aintea.sg.scenarios.CabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.sg.scenarios.CabinetBuilder.*;

public class CabinetTest extends LoadApproximatorTest {

    @Override
    protected void createSubstation() {
        substation = CabinetBuilder.build();
    }

    private static Arguments[] openCloseF5F6() {
       return Data.generateAllPossibilitiesWithValues(3, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6() {
        return Data.generateAllPossibilitiesWithValues(3, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF2F3F4F5F6() {
        return Data.generateAllPossibilitiesWithValues(3, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    private void test(String[] toOpen, Double[] consumptions, double[] expectedCables, double[] expectedFuses) {
        var fuseCables = new String[]{F1_NAME, F3_NAME, F4_NAME};
        var fuses = new String[] {F1_NAME, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME};
        genericTest(toOpen, consumptions, expectedCables, expectedFuses, fuseCables, fuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc1_testAllClosed(String[] toOpen, Double[] consumptions) {
        var loadCbl = consumptions[0] + consumptions[1] + consumptions[2];
        var expectedCables = new double[] {
                loadCbl,
                consumptions[1],
                consumptions[2]
        };
        var expectedFuses = new double[] {
                loadCbl,
                consumptions[0]-loadCbl,
                consumptions[1],
                consumptions[2],
                0,
                0
        };
        test(toOpen, consumptions, expectedCables, expectedFuses);
    }


    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc2_testF4Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1],
                consumptions[1],
                0
        };
        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1],
                -consumptions[1],
                consumptions[1],
                0,
                0,
                0
        };
        test(Utils.merge(toOpen, F4_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc3_testF3Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[2],
                0,
                consumptions[2]
        };
        var expectedFuses = new double[] {
                consumptions[0] + consumptions[2],
                -consumptions[2],
                0,
                consumptions[2],
                0,
                0
        };
        test(Utils.merge(toOpen, F3_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc4_testF3F4Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {consumptions[0], 0, 0};
        var expectedFuses = new double[] {consumptions[0], 0, 0, 0, 0, 0};
        test(Utils.merge(toOpen, F3_NAME, F4_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6")
    public void sc5_testF2Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {consumptions[0],0,0};
        var expectedFuses = new double[] {consumptions[0],0,0,0,0,0,0};
        test(Utils.merge(toOpen, F2_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F3F4F5F6")
    public void sc6_testF1Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {0,0,0};
        var expectedFuses = new double[] {0,0,0,0,0,0};
        test(Utils.merge(toOpen, F1_NAME), consumptions, expectedCables, expectedFuses);
    }

}
