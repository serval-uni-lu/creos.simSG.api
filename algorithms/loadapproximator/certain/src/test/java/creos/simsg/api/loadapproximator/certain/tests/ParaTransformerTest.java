package creos.simsg.api.loadapproximator.certain.tests;

import creos.simsg.api.model.Substation;
import creos.simsg.api.scenarios.ParaTransformerSC;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static creos.simsg.api.scenarios.ParaTransformerSC.*;


public class ParaTransformerTest extends LoadApproximatorTest {
    @Override
    protected Substation createSubstation() {
        return new ScenarioBuilder()
                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
                .build()
                .getGrid()
                .getSubstation(ParaTransformerSC.SUBSTATION_NAME)
                .get();
    }

    @Override
    protected String[] getFuses() {
        return new String[] {F1_NAME, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME};
    }

    @Override
    protected String[] getFuseCables() {
        return new String[]{F1_NAME, F3_NAME, F5_NAME};
    }

    private static Arguments[] openCloseF6() {
        return TestHelper.generateAllPossibilitiesWithValues(3, F6_NAME);
    }

    private static Arguments[] openCloseF2F6() {
        return TestHelper.generateAllPossibilitiesWithValues(3, F2_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF5F6() {
        return TestHelper.generateAllPossibilitiesWithValues(3, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF4F5F6() {
        return TestHelper.generateAllPossibilitiesWithValues(3, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF2F5F6() {
        return TestHelper.generateAllPossibilitiesWithValues(3, F2_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF2F4F5F6() {
        return TestHelper.generateAllPossibilitiesWithValues(3, F2_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc1_testOpenCloseDE(String[] toOpen, Double[] consumption) {
        var expectedCbl1 = (consumption[0] + consumption[1] + consumption[2])/2;
        var expectedCables = new double[] {
                expectedCbl1,
                expectedCbl1,
                consumption[2]
        };

        var expectedFuses = new double[] {
                expectedCbl1,
                consumption[0] - expectedCbl1,
                expectedCbl1,
                consumption[1] - expectedCbl1,
                consumption[2],
                0
        };
        genericTest(toOpen, consumption, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc2_testF4F5Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0],
                consumption[1],
                0
        };
        var expectedFuse = new double[] {
                consumption[0],
                0,
                consumption[1],
                0,
                0,
                0
        };
        consumption[2] = 0.;
        genericTest(TestHelper.merge(toOpen, F4_NAME, F5_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc3_testF4OPen(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0] + consumption[2],
                consumption[1],
                consumption[2]
        };

        var expectedFuse = new double[] {
                consumption[0] + consumption[2],
                -consumption[2],
                consumption[1],
                0,
                consumption[2],
                0
        };
        genericTest(TestHelper.merge(toOpen, F4_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc4_testF3Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0] + consumption[1] + consumption[2],
                consumption[1],
                consumption[2]
        };
        var expectedFuse = new double[] {
                consumption[0] + consumption[1] + consumption[2],
                - consumption[1] - consumption[2],
                0,
                consumption[1],
                consumption[2],
                0
        };
        genericTest(TestHelper.merge(toOpen, F3_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc5_testF3F5Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0] + consumption[1],
                consumption[1],
                0
        };
        var expectedFuse = new double[] {
                consumption[0] + consumption[1] ,
                -consumption[1],
                0,
                consumption[1],
                0,
                0
        };
        consumption[2] = 0.;
        genericTest(TestHelper.merge(toOpen, F3_NAME, F5_NAME), consumption, expectedCbl, expectedFuse);
    }

    @Disabled("https://github.com/UL-SnT-Serval/creos.simSG.api/issues/14")
    @ParameterizedTest
    @MethodSource("openCloseF2F6")
    public void sc6_testF3F4F5Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
            consumption[0],
                0,
                0
        };
        var expectedFuse = new double[] {
                consumption[0],
                0,
                0,
                0,
                0,
                0,
        };
        consumption = new Double[]{consumption[0], 0., 0.};
        genericTest(TestHelper.merge(toOpen, F3_NAME, F4_NAME, F5_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc7_testF2Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0],
                consumption[1] + consumption[2],
                consumption[2]
        };
        var expectedFuse = new double[] {
                consumption[0],
                0,
                consumption[1] + consumption[2],
                -consumption[2],
                consumption[2],
                0
        };
        genericTest(TestHelper.merge(toOpen, F2_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc8_testF2F5Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0],
                consumption[1],
                0
        };
        var expectedFuse = new double[] {
                consumption[0],
                0,
                consumption[1],
                0,
                0,
                0
        };
        consumption[2] = 0.;
        genericTest(TestHelper.merge(toOpen, F2_NAME, F5_NAME), consumption, expectedCbl, expectedFuse);
    }

    @Disabled("https://github.com/UL-SnT-Serval/creos.simSG.api/issues/14")
    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc9_testF2F4Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0],
                consumption[1],
                0,
        };
        var expectedFuse = new double[] {
                consumption[0],
                0,
                consumption[1],
                0,
                0,
                0

        };
        consumption[2] = 0.;
        genericTest(TestHelper.merge(toOpen, F2_NAME, F4_NAME), consumption, expectedCbl, expectedFuse);
    }

    @Disabled("https://github.com/UL-SnT-Serval/creos.simSG.api/issues/14")
    @ParameterizedTest
    @MethodSource("openCloseF4F5F6")
    public void sc10_testF2F3Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0],
                0,
                0
        };
        var expectedFuse = new double[] {
                consumption[0],
                0,
                0,
                0,
                0,
                0
        };
        consumption = new Double[]{consumption[0], 0., 0.};
        genericTest(TestHelper.merge(toOpen, F2_NAME, F3_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc11_testF1Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0],
                consumption[0] + consumption[1] + consumption[2],
                consumption[2]
        };
        var expectedFuse = new double[] {
                0,
                consumption[0],
                consumption[0] + consumption[1] + consumption[2],
                -consumption[0] - consumption[2],
                consumption[2],
                0
        };
        genericTest(TestHelper.merge(toOpen, F1_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc12_testF1F5Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                consumption[0],
                consumption[0] + consumption[1],
                0
        };
        var expectedFuse = new double[] {
                0,
                consumption[0],
                consumption[0] + consumption[1],
                -consumption[0],
                0,
                0
        };
        consumption[2] = 0.;
        genericTest(TestHelper.merge(toOpen, F1_NAME, F5_NAME), consumption, expectedCbl, expectedFuse);
    }

    @Disabled("https://github.com/UL-SnT-Serval/creos.simSG.api/issues/14")
    @ParameterizedTest
    @MethodSource("openCloseF2F5F6")
    public void sc13_testF1F4Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                0,
                consumption[1],
                0
        };
        var expectedFuse = new double[] {
                0,
                0,
                consumption[1],
                0,
                0,
                0
        };
        consumption = new Double[]{0., consumption[1], 0.};
        genericTest(TestHelper.merge(toOpen, F1_NAME, F4_NAME), consumption, expectedCbl, expectedFuse);
    }

    @Disabled("https://github.com/UL-SnT-Serval/creos.simSG.api/issues/14")
    @ParameterizedTest
    @MethodSource("openCloseF2F4F5F6")
    public void sc14_testF1F3Open(String[] toOpen) {
        var expectedCbl = new double[] {0,0,0};
        var expectedFuse = new double[] {0,0,0,0,0,0};
        var consumption = new Double[]{0., 0., 0.};
        genericTest(TestHelper.merge(toOpen, F1_NAME, F3_NAME), consumption, expectedCbl, expectedFuse);
    }

    @Disabled("https://github.com/UL-SnT-Serval/creos.simSG.api/issues/14")
    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc15_testF1F2Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                0,
                consumption[1] + consumption[2],
                consumption[2]
        };
        var expectedFuse = new double[] {
                0,
                0,
                consumption[1] + consumption[2],
                -consumption[2],
                consumption[2],
                0
        };
        consumption[0] = 0.;
        genericTest(TestHelper.merge(toOpen, F1_NAME, F2_NAME), consumption, expectedCbl, expectedFuse);
    }

    @Disabled("https://github.com/UL-SnT-Serval/creos.simSG.api/issues/14")
    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc16_testF1F2F5Open(String[] toOpen, Double[] consumption) {
        var expectedCbl = new double[] {
                0,
                consumption[1],
                0
        };
        var expectedFuse = new double[] {
                0,
                0,
                consumption[1],
                0,
                0,
                0
        };
        consumption = new Double[]{0., consumption[1], 0.};
        genericTest(TestHelper.merge(toOpen, F1_NAME, F2_NAME, F5_NAME), consumption, expectedCbl, expectedFuse);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc17_testF5Open(String[] toOpen, Double[] consumption) {
        var cableLoad = (consumption[0] + consumption[1])/2;
        var expectedCbl = new double[] {
                cableLoad,
                cableLoad,
                0
        };
        var expectedFuse = new double[] {
                cableLoad,
                consumption[0] - cableLoad,
                cableLoad,
                consumption[1] - cableLoad,
                0,
                0
        };
        consumption[2] = 0.;
        genericTest(TestHelper.merge(toOpen, F5_NAME), consumption, expectedCbl, expectedFuse);
    }

}
