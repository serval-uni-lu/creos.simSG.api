package duc.sg.java.matrix.certain;

import duc.sg.java.scenarios.ParaWithDeadendSC;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import org.junit.jupiter.api.Test;

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
        genericTest(expected, ParaWithDeadendSC.F1_NAME);
    }

    @Test
    public void sc3_f3Open() {
        var expected = new double[] {
                1,1,0,
                0,1,1,
                0,0,1,

        };
        genericTest(expected, ParaWithDeadendSC.F3_NAME);
    }

    @Test
    public void sc4_f4Open() {
        var expected = new double[] {
                1,0,
                0,1,
        };
        genericTest(expected, ParaWithDeadendSC.F4_NAME);
    }

    @Test
    public void sc4_f2Open() {
        var expected = new double[] {
                1,0,
                0,1,
        };
        genericTest(expected, ParaWithDeadendSC.F2_NAME);
    }

    @Test
    public void sc4_f1f2Open() {
        var expected = new double[] {
                1
        };
        genericTest(expected, ParaWithDeadendSC.F1_NAME, ParaWithDeadendSC.F2_NAME);
    }

}
