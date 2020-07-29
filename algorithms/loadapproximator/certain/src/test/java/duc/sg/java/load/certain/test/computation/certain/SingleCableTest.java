package duc.sg.java.load.certain.test.computation.certain;

import duc.sg.java.load.certain.test.TestHelper;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.sg.java.scenarios.SingleCableSC.F1_NAME;
import static duc.sg.java.scenarios.SingleCableSC.F2_NAME;

public class SingleCableTest extends LoadApproximatorTest{


    private static Arguments[] threeValues() {
        return TestHelper.generateRandomDoubles(3);
    }

    @Override
    protected void createSubstation() {
        substation = new ScenarioBuilder()
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

    @ParameterizedTest
    @MethodSource("threeValues")
    public void testFSubsOpen(double m1Cons, double m2Cons, double m3Cons) {
        var consumptions = new Double[]{m1Cons + m2Cons + m3Cons};
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


    @ParameterizedTest
    @MethodSource("threeValues")
    public void testAllOpen(double m1Cons, double m2Cons, double m3Cons) {
        var consumptions = new Double[]{m1Cons + m2Cons + m3Cons};
        var openFuses = new String[]{F1_NAME, F2_NAME};

        var expectedCableLoad = new double[] {0};
        var expectedFuseLoad = new double[]{
                0,
                0.
        };

        genericTest(openFuses, consumptions, expectedCableLoad, expectedFuseLoad);
    }

}
