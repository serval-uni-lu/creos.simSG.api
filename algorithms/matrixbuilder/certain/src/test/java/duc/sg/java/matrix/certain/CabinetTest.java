package duc.sg.java.matrix.certain;

import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.sg.java.scenarios.CabinetSC.*;


public class  CabinetTest extends MatrixBuilderTest {

    @Override
    protected void createSubstation() {
        substation = new ScenarioBuilder()
                .chooseScenario(ScenarioName.CABINET)
                .build()
                .getSubstation();
    }

    private static Arguments[] openCloseF5F6() {
        return TestHelper.generateAllPossibilities(F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6() {
        return TestHelper.generateAllPossibilities(F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseOthers() {
        return TestHelper.generateAllPossibilities(F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }



    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc1_testAllClosed(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,
                0,1,1,1,
                0,0,1,0,
                0,0,0,1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc2_testF4Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,1,1,
                0,0,1,
        };
        genericTest(expected, TestHelper.merge(toOpen, F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc3_testF3Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,1,1,
                0,0,1,
        };
        genericTest(expected, TestHelper.merge(toOpen, F3_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc4_testF3F4Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, TestHelper.merge(toOpen, F3_NAME, F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6")
    public void sc5_testF2Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, TestHelper.merge(toOpen, F2_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseOthers")
    public void sc6_testF1Open(String[] toOpen) {
        var expected = new double[] {0};
        genericTest(expected, TestHelper.merge(toOpen, F1_NAME));
    }



}
