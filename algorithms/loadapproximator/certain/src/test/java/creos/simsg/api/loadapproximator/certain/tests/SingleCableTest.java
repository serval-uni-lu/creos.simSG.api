package creos.simsg.api.loadapproximator.certain.tests;

import creos.simsg.api.model.Substation;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static creos.simsg.api.scenarios.SingleCableSC.F1_NAME;
import static creos.simsg.api.scenarios.SingleCableSC.F2_NAME;

public class SingleCableTest extends LoadApproximatorTest{


    private static Arguments[] threeValues() {
        return TestHelper.generateRandomDoubles(3);
    }

    @Override
    protected Substation createSubstation() {
        return new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .build()
                .getSubstation();
    }

    @Override
    protected String[] getFuses() {
        return new String[]{F1_NAME, F2_NAME};
    }

    @Override
    protected String[] getFuseCables() {
        return new String[]{F1_NAME};
    }

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testAllClosed(double m1Cons, double m2Cons, double m3Cons) {
        var consumptions = new Double[]{m1Cons + m2Cons + m3Cons};

        var expectedCableLoad = new double[] {consumptions[0]};
        var expectedFuseLoad = new double[]{
                consumptions[0],
                0.
        };

        genericTest(new String[]{}, consumptions, expectedCableLoad, expectedFuseLoad);
    }

    @Test
    public void testFSubsOpen() {
        var consumptions = new Double[]{0.};
        var openFuses = new String[]{F1_NAME};

        var expectedCableLoad = new double[] {0};
        var expectedFuseLoad = new double[]{
                0,
                0.
        };
        genericTest(openFuses, consumptions, expectedCableLoad, expectedFuseLoad);
    }

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testFCabOpen(double m1Cons, double m2Cons, double m3Cons) {
        var consumptions = new Double[]{m1Cons + m2Cons + m3Cons};
        var openFuses = new String[]{F2_NAME};

        var expectedCableLoad = new double[] {consumptions[0]};
        var expectedFuseLoad = new double[]{
                consumptions[0],
                0.
        };

        genericTest(openFuses, consumptions, expectedCableLoad, expectedFuseLoad);
    }


    @Test
    public void testAllOpen() {
        var consumptions = new Double[]{0.};
        var openFuses = new String[]{F1_NAME, F2_NAME};

        var expectedCableLoad = new double[] {0};
        var expectedFuseLoad = new double[]{
                0,
                0.
        };

        genericTest(openFuses, consumptions, expectedCableLoad, expectedFuseLoad);
    }

}
