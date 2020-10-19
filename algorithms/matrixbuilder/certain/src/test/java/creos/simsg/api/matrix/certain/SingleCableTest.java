package creos.simsg.api.matrix.certain;

import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import creos.simsg.api.scenarios.SingleCableSC;
import org.junit.jupiter.api.Test;


public class SingleCableTest extends MatrixBuilderTest {

    /*
        subs1-[f1]----(cbl1)----[f2]-c1
     */
    @Override
    protected void createSubstation() {
        substation = new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .build()
                .getSubstation();
    }


    @Test
    public void testScenario1_allClose() {
        genericTest(new double[]{1});
    }

    @Test
    public void testScenario1_f1Open() {
        genericTest(new double[]{0}, SingleCableSC.F1_NAME);
    }

    @Test
    public void testScenario1_f2Open() {
        genericTest(new double[]{1}, SingleCableSC.F2_NAME);
    }

    @Test
    public void testScenario1_allOpen() {
        genericTest(new double[]{0}, SingleCableSC.F1_NAME, SingleCableSC.F2_NAME);
    }
}
