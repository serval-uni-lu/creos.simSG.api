package duc.sg.java.matrix.certain.test;

import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import duc.sg.java.scenarios.SingleCableSC;
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
                .getGrid()
                .getSubstation(SingleCableSC.SUBSTATION_NAME)
                .get();
    }


    @Test
    public void testScenario1_allClose() {
        genericTest(new double[]{1});
    }

    @Test
    public void testScenario1_f1Open() {
        fusesMap.get(SingleCableSC.F1_NAME).openFuse();
        genericTest(new double[]{0});
    }

    @Test
    public void testScenario1_f2Open() {
        fusesMap.get(SingleCableSC.F2_NAME).openFuse();
        genericTest(new double[]{1});
    }

    @Test
    public void testScenario1_allOpen() {
        fusesMap.get(SingleCableSC.F1_NAME).openFuse();
        fusesMap.get(SingleCableSC.F2_NAME).openFuse();

        genericTest(new double[]{0});
    }
}
