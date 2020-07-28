package duc.sg.java.matrix.certain;

import duc.sg.java.scenarios.ParaTransformerSC;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.sg.java.scenarios.ParaTransformerSC.*;


public class ParaTransformerTest extends MatrixBuilderTest {

    @Override
    protected void createSubstation() {
        substation = new ScenarioBuilder()
                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
                .build()
                .getGrid()
                .getSubstation(ParaTransformerSC.SUBSTATION_NAME)
                .get();
    }

    private static Arguments[] openCloseF6() {
        return TestHelper.generateAllPossibilities(F6_NAME);
    }

    private static Arguments[] openCloseF2F6() {
        return TestHelper.generateAllPossibilities(F2_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF5F6() {
        return TestHelper.generateAllPossibilities(F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF4F5F6() {
        return TestHelper.generateAllPossibilities(F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF2F5F6() {
        return TestHelper.generateAllPossibilities(F2_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF2F4F5F6() {
        return TestHelper.generateAllPossibilities(F2_NAME, F4_NAME, F5_NAME, F6_NAME);
    }


    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc1_testOpenCloseDE(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0, 0, 0,
                1, 0, -1, 0, 0,
                0, 1, 0, 1, 1,
                0, 0, 1, 1, 0,
                0, 0, 0, 0, 1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc2_testF4F5Open(String[] toOpen) {
        var expected = new double[]{
                1, 0,
                0, 1
        };
        genericTest(expected, TestHelper.merge(toOpen, F4_NAME, F5_NAME));

    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc3_testF4OPen(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0, 0,
                0, 0, 1, 0,
                0, 1, 0, 1,
                0, 0, 0, 1,
        };
        genericTest(expected, TestHelper.merge(toOpen, F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc4_testF3Open(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0, 0,
                0, 1, 1, 1,
                0, 0, 1, 0,
                0, 0, 0, 1,
        };

        genericTest(expected, TestHelper.merge(toOpen, F3_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc5_testF3F5Open(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0,
                0, 1, 1,
                0, 0, 1,
        };

        genericTest(expected, TestHelper.merge(toOpen, F3_NAME, F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F6")
    public void sc6_testF3F4F5Open(String[] toOpen) {
        var expected = new double[]{1};
        genericTest(expected, TestHelper.merge(toOpen, F3_NAME, F4_NAME, F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc7_testF2Open(String[] toOpen) {
        var expected = new double[]{
                1, 0, 0, 0,
                0, 1, 1, 0,
                0, 0, 1, 1,
                0, 0, 0, 1,
        };
        genericTest(expected, TestHelper.merge(toOpen, F2_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc8_testF2F5Open(String[] toOpen) {
        var expected = new double[]{
                1, 0,
                0, 1,
        };
        genericTest(expected, TestHelper.merge(toOpen, F2_NAME, F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc9_testF2F4Open(String[] toOpen) {
        var expected = new double[]{
                1, 0,
                0, 1,
        };
        genericTest(expected, TestHelper.merge(toOpen, F2_NAME, F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F5F6")
    public void sc10_testF2F3Open(String[] toOpen) {
        var expected = new double[]{1};
        genericTest(expected, TestHelper.merge(toOpen, F2_NAME, F3_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc11_testF1Open(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0, 0,
                0, 1, 1, 1,
                0, 0, 1, 0,
                0, 0, 0, 1,
        };

        genericTest(expected, TestHelper.merge(toOpen, F1_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc12_testF1F5Open(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0,
                0, 1, 1,
                0, 0, 1,
        };

        genericTest(expected, TestHelper.merge(toOpen, F1_NAME, F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F5F6")
    public void sc13_testF1F4Open(String[] toOpen) {
        var expected = new double[]{1};
        genericTest(expected, TestHelper.merge(toOpen, F1_NAME, F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F4F5F6")
    public void sc14_testF1F3Open(String[] toOpen) {
        var expected = new double[]{0};
        genericTest(expected, TestHelper.merge(toOpen, F1_NAME, F3_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc15_testF1F2Open(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0,
                0, 1, 1,
                0, 0, 1,
        };

        genericTest(expected, TestHelper.merge(toOpen, F1_NAME, F2_NAME));
    }


    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc16_testF1F2F5Open(String[] toOpen) {
        var expected = new double[]{1};
        genericTest(expected, TestHelper.merge(toOpen, F1_NAME, F2_NAME, F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc17_testF5Open(String[] toOpen) {
        var expected = new double[]{
                1, 1, 0, 0,
                1, 0, -1, 0,
                0, 1, 0, 1,
                0, 0, 1, 1,
        };
        genericTest(expected, TestHelper.merge(toOpen, F5_NAME));
    }


}
