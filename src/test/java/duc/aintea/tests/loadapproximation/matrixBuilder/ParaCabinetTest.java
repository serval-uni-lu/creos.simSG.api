package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.sg.scenarios.ParaCabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.tests.sg.scenarios.ParaCabinetBuilder.*;

public class ParaCabinetTest extends MatriceBuilderTest{

    @Override
    protected void createSubstation() {
        substation = ParaCabinetBuilder.build();
    }

    private static Arguments[] openCloseF8() {
        return Utils.generator(F8_NAME);
    }

    private static Arguments[] openCloseF2F3F4F5F6F7F8OpenF1() {
        return Utils.generator(new String[]{F1_NAME}, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME, F7_NAME, F8_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6F7F8OpenF2() {
        return Utils.generator(new String[]{F2_NAME}, F3_NAME, F4_NAME, F5_NAME, F6_NAME, F7_NAME, F8_NAME);
    }

    private static Arguments[] openCloseF4F6F7F8OpenF3F5() {
        return Utils.generator(new String[]{F3_NAME, F5_NAME}, F4_NAME, F6_NAME, F7_NAME, F8_NAME);
    }

    private static Arguments[] openCloseF7F8OpenF4F6() {
        return Utils.generator(new String[]{F4_NAME, F6_NAME}, F7_NAME, F8_NAME);
    }


    private static Arguments[] openCloseF8OpenF7() {
        return Utils.generator(new String[]{F7_NAME}, F8_NAME);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc1_allClosed(String[] toOpen) {
        double[] expected = new double[] {
                1,1,0,0,0,0,0,
                0,0,1,1,0,0,0,
                0,0,0,0,1,1,0,
                0,0,0,0,0,0,1,
                0,1,1,0,1,0,0,
                0,0,0,1,0,1,1,
                0,0,1,0,-1,0,0

        };
        genericTest(expected, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F3F4F5F6F7F8OpenF1")
    public void sc2_f1Open(String[] toOpen) {
        genericTest(new double[]{0}, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6F7F8OpenF2")
    public void sc3_f2Open(String[] toOpen) {
        genericTest(new double[]{1}, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F6F7F8OpenF3F5")
    public void sc4_f3f5Open(String[] toOpen) {
        genericTest(new double[]{1}, toOpen);
    }

    @ParameterizedTest
    @MethodSource("openCloseF7F8OpenF4F6")
    public void sc5_f4f6Open(String[] toOpen) {
        double[] expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1,
        };
        genericTest(expected, toOpen);
    }

//    @ParameterizedTest
//    @MethodSource("openCloseF8OpenF7")
//    public void sc6_f7Open(String[] toOpen) {
//        double[] expected = new double[] {
//                1,1,0,0,0,0,
//                0,0,1,1,0,0,
//                0,0,0,0,1,1,
//                0,1,1,0,1,0,
//                0,0,0,1,0,1,
//                0,0,1,0,-1,0
//        };
//        genericTest(expected, toOpen);
//    }

}
