package creos.simsg.api.matrix.certain;

import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static creos.simsg.api.scenarios.ParaWithDeadendSC.*;

public class ParaDeadEndTest extends MatrixBuilderTest {


    @Override
    protected void createSubstation() {
        substation = new ScenarioBuilder()
                .chooseScenario(ScenarioName.PARA_W_DEADEND)
                .build()
                .getSubstation();
    }

    @Test
    public void sc1_allClosed() {
        var expected = new double[] {
                1,1,0,0,
                1,0,-1,0,
                0,1,0,1,
                0,0,1,1,
        };
        genericTest(expected);
    }

    @Test
    public void sc2_f1Open() {
        var expected = new double[] {
                1,1,0,
                0,1,1,
                0,0,1,

        };
        genericTest(expected, F1_NAME);
    }

    @Test
    public void sc3_f3Open() {
        var expected = new double[] {
                1,1,0,
                0,1,1,
                0,0,1,

        };
        genericTest(expected, F3_NAME);
    }

    @Test
    public void sc4_f4Open() {
        var expected = new double[] {
                1,0,
                0,1,
        };
        genericTest(expected, F4_NAME);
    }

    @Test
    public void sc5_f2Open() {
        var expected = new double[] {
                1,0,
                0,1,
        };
        genericTest(expected, F2_NAME);
    }

    private static Arguments[] openCloseF4() {
        return TestHelper.generateAllPossibilities(F4_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4")
    public void sc6_f1f2Open(String[] toOpen) {
        var expected = new double[] {
                1
        };
        genericTest(expected, TestHelper.merge(toOpen, F1_NAME, F2_NAME));
    }

    private static Arguments[] openCloseF2F4() {
        return TestHelper.generateAllPossibilities(F2_NAME, F4_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F4")
    public void sc7_f1f3Open(String[] toOpen) {
        var expected = new double[] {
                0
        };
        genericTest(expected, TestHelper.merge(toOpen, F1_NAME, F3_NAME));
    }

    @Test
    public void sc8_f1f4Open() {
        var expected = new double[] {
                1
        };
        genericTest(expected, F1_NAME, F4_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4")
    public void sc9_f2f3Open(String[] toOpen) {
        var expected = new double[] {
                1
        };
        genericTest(expected, TestHelper.merge(toOpen, F2_NAME, F3_NAME));
    }

    @Test
    public void sc10_f2f4Open() {
        var expected = new double[] {
                1,0,
                0,1
        };
        genericTest(expected, F2_NAME, F4_NAME);
    }

    @Test
    public void sc11_f3f4Open() {
        var expected = new double[] {
                1,
        };
        genericTest(expected, F3_NAME, F4_NAME);
    }



}
