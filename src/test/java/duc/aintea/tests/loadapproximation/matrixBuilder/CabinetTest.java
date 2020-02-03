package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.sg.scenarios.CabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.tests.sg.scenarios.CabinetBuilder.*;

public class CabinetTest extends MatriceBuilderTest {

    @Override
    protected void createSubstation() {
        substation = CabinetBuilder.build(0);
    }

    private static Arguments[] openCloseF5F6() {
        return Utils.generator(F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF5F6OpenF4() {
        return Utils.generator(new String[]{F4_NAME}, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF5F6OpenF3() {
        return Utils.generator(new String[]{F3_NAME}, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF5F6OpenF3F4() {
        return Utils.generator(new String[]{F3_NAME, F4_NAME}, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6OpenF2() {
        return Utils.generator(new String[]{F2_NAME}, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openF1OpenCloseOthers() {
        return Utils.generator(new String[]{F1_NAME}, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc1_testAllClosed(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6OpenF4")
    public void sc2_testF4Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6OpenF3")
    public void sc3_testF3Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6OpenF3F4")
    public void sc4_testF3F4Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6OpenF2")
    public void testAllClosedButF1(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openF1OpenCloseOthers")
    public void testAll(String[] toOpen) {
        var expected = new double[] {0};
        genericTest(expected, toOpen);
    }

}
