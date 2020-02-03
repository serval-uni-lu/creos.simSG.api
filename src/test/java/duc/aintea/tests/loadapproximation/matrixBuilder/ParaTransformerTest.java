package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.sg.scenarios.ParaTransformerBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.tests.sg.scenarios.ParaTransformerBuilder.*;

public class ParaTransformerTest extends MatriceBuilderTest {

    @Override
    protected void createSubstation() {
        substation = ParaTransformerBuilder.build();
    }

    private static Arguments[] openCloseF6() {
        return Utils.generator(F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF5F4() {
        return Utils.generator(new String[]{F5_NAME, F4_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF4() {
        return Utils.generator(new String[]{F4_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF3() {
        return Utils.generator(new String[]{F3_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF3F5() {
        return Utils.generator(new String[]{F3_NAME, F5_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF2F6OpenF3F4F5() {
        return Utils.generator(new String[]{F3_NAME, F4_NAME, F5_NAME}, F2_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF2() {
        return Utils.generator(new String[]{F2_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF2F5() {
        return Utils.generator(new String[]{F2_NAME, F5_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF5F6OpenF2F4() {
        return Utils.generator(new String[]{F2_NAME, F4_NAME}, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF4F5F6OpenF2F3() {
        return Utils.generator(new String[]{F2_NAME, F3_NAME}, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF1() {
        return Utils.generator(new String[]{F1_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF1F5() {
        return Utils.generator(new String[]{F1_NAME, F5_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF2F5F6OpenF1F4() {
        return Utils.generator(new String[]{F1_NAME, F4_NAME}, F2_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF2F4F5F6OpenF1F3() {
        return Utils.generator(new String[]{F1_NAME, F3_NAME}, F2_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF1F2() {
        return Utils.generator(new String[]{F1_NAME, F2_NAME}, F6_NAME);
    }

    private static Arguments[] openCloseF6OpenF5() {
//        return Utils.generator(new String[]{F5_NAME}, F6_NAME);
        return Utils.generator(new String[]{F5_NAME}, new String[]{});
    }




    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc1_testOpenCloseDE(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,
                0,0,1,1,0,
                0,0,0,0,1,
                0,1,0,1,1,
                1,0,-1,0,0,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF5F4")
    public void sc2_testF4F5Open(String[] toOpen) {
        var expected = new double[] {
                1,0,
                0,1
        };
        genericTest(expected, toOpen);

    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF4")
    public void sc3_testF4OPen(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,0,1
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF3")
    public void sc4_testF3Open(String[] toOpen) {
        var expected = new double[] {
          1,1,0,0,
          0,0,1,0,
          0,0,0,1,
          0,1,1,1,
        };

        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF3F5")
    public void sc5_testF3F5Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };

        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F6OpenF3F4F5")
    public void sc6_testF3F4F5Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF2")
    public void sc7_testF2Open(String[] toOpen) {
        var expected = new double[] {
                1,0,0,0,
                0,1,1,0,
                0,0,0,1,
                0,0,1,1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF2F5")
    public void sc8_testF2F5Open(String[] toOpen) {
        var expected = new double[] {
                1,0,
                0,1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6OpenF2F4")
    public void sc9_testF2F4Open(String[] toOpen) {
        var expected = new double[] {
                1,0,
                0,1,
        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F5F6OpenF2F3")
    public void sc10_testF2F4Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF1")
    public void sc11_testF1Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1,
        };

        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF1F5")
    public void sc12_testF1F5Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };

        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F5F6OpenF1F4")
    public void sc13_testF1F4Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F4F5F6OpenF1F3")
    public void sc14_testF1F3Open(String[] toOpen) {
        var expected = new double[]{0};
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF1F2")
    public void sc15_testF1F2Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };

        genericTest(expected, toOpen);
    }

    private static Arguments[] openCloseF6OpenF1F2F5() {
        return Utils.generator(new String[]{F1_NAME, F2_NAME, F5_NAME}, F6_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF1F2F5")
    public void sc16_testF1F2F5Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6OpenF5")
    public void sc17_testF5Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,
                0,0,1,1,
                0,1,0,1,
                1,0,-1,0
        };
        genericTest(expected, toOpen);
    }



}
