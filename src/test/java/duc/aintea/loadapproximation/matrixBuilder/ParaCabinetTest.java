package duc.aintea.loadapproximation.matrixBuilder;

import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.loadapproximation.generator.Utils;
import duc.aintea.sg.scenarios.ParaCabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ParaCabinetTest extends MatrixBuilderTest {

    @Override
    protected void createSubstation() {
        substation = ParaCabinetBuilder.build();
    }

    private static Arguments[] openCloseF8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF2F3F4F5F6F7F8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F2_NAME, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F5_NAME, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6F7F8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F5_NAME, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF4F7F8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF4F6F7F8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF6F7F8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF7F8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF4F8() {
        return Data.generateAllPossibilities(ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F8_NAME);
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
    @MethodSource("openCloseF2F3F4F5F6F7F8")
    public void sc2_f1Open(String[] toOpen) {
        genericTest(new double[]{0}, Utils.merge(toOpen, ParaCabinetBuilder.F1_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6F7F8")
    public void sc3_f2Open(String[] toOpen) {
        genericTest(new double[]{1}, Utils.merge(toOpen, ParaCabinetBuilder.F2_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F6F7F8")
    public void sc4_f3f5Open(String[] toOpen) {
        genericTest(new double[]{1}, Utils.merge(toOpen, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF7F8")
    public void sc5_f4f6Open(String[] toOpen) {
        double[] expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F6_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc6_f7Open(String[] toOpen) {
        double[] expected = new double[] {
                1,1,0,0,0,0,
                0,0,1,1,0,0,
                0,0,0,0,1,1,
                0,1,1,0,1,0,
                0,0,0,1,0,1,
                0,0,1,0,-1,0
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F7_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc7_f6Open(String[] toOpen) {
        double[] expected = new double[] {
                1,1,0,0,0,0,
                0,0,1,1,0,0,
                0,0,0,0,1,0,
                0,0,0,0,0,1,
                0,1,1,0,1,0,
                0,0,0,1,0,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F6_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc8_f6f7Open(String[] toOpen) {
        double[] expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F6_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc9_f5Open(String[] toOpen){
        double[] expected = new double[] {
                1,1,0,0,0,0,
                0,0,1,1,0,0,
                0,0,0,0,1,0,
                0,0,0,0,0,1,
                0,1,1,0,0,0,
                0,0,0,1,1,1
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc10_f5f7Open(String[] toOpen){
        double[] expected = new double[] {
                1,1,0,0,0,
                0,0,1,1,0,
                0,0,0,0,1,
                0,1,1,0,0,
                0,0,0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F5_NAME, ParaCabinetBuilder.F7_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc11_f5f6Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,
                0,0,1,1,0,
                0,0,0,0,1,
                0,1,1,0,0,
                0,0,0,1,1
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F5_NAME, ParaCabinetBuilder.F6_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F8")
    public void sc12_f5f6f7Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F5_NAME, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc13_f5f6f7Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,0,
                0,0,1,0,0,0,
                0,0,0,1,1,0,
                0,0,0,0,0,1,
                0,1,1,1,0,0,
                0,0,0,0,1,1
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF6F7F8")
    public void sc14_f4f5Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F5_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F7F8")
    public void sc15_f3f6Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F6_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc16_f3f7Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,
                0,0,1,1,0,
                0,0,0,0,1,
                0,1,1,0,0,
                0,0,0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F7_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc17_f3Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,0,
                0,0,1,1,0,0,
                0,0,0,0,1,0,
                0,0,0,0,0,1,
                0,1,1,0,0,0,
                0,0,0,1,1,1
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F3_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc18_f3f4Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,0,0,
                0,0,1,1,0,
                0,0,0,0,1,
                0,1,1,0,0,
                0,0,0,1,1
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc19_f3f4f4Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F7_NAME));
    }

}
