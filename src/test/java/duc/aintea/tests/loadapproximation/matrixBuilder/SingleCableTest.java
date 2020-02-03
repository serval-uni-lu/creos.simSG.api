package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.sg.scenarios.SingleCableBuilder;
import org.junit.jupiter.api.Test;

import static duc.aintea.tests.sg.scenarios.SingleCableBuilder.*;

public class SingleCableTest extends MatriceBuilderTest {

    /*
        subs1-[f1]----(cbl1)----[f2]-c1
     */
    @Override
    protected void createSubstation() {
        substation = SingleCableBuilder.build(0);
    }


    @Test
    public void testScenario1_allClose() {
        genericTest(new double[]{1});
    }

    @Test
    public void testScenario1_f1Open() {
        fusesMap.get(F1_NAME).openFuse();
        genericTest(new double[]{0});
    }

    @Test
    public void testScenario1_f2Open() {
        fusesMap.get(F2_NAME).openFuse();
        genericTest(new double[]{1});
    }

    @Test
    public void testScenario1_allOpen() {
        fusesMap.get(F1_NAME).openFuse();
        fusesMap.get(F2_NAME).openFuse();

        genericTest(new double[]{0});
    }
}
