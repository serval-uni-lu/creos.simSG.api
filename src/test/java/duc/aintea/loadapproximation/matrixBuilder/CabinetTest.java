package duc.aintea.loadapproximation.matrixBuilder;

import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.loadapproximation.generator.Utils;
import duc.aintea.sg.scenarios.CabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.sg.scenarios.CabinetBuilder.*;

public class CabinetTest extends MatrixBuilderTest {

    @Override
    protected void createSubstation() {
        substation = CabinetBuilder.build();
    }

    private static Arguments[] openCloseF5F6() {
        return Data.generateAllPossibilities(F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6() {
        return Data.generateAllPossibilities(F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseOthers() {
        return Data.generateAllPossibilities(F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
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
    @MethodSource("openCloseF5F6")
    public void sc2_testF4Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc3_testF3Open(String[] toOpen) {
        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        genericTest(expected, Utils.merge(toOpen, F3_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc4_testF3F4Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, Utils.merge(toOpen, F3_NAME, F4_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6")
    public void sc5_testF2Open(String[] toOpen) {
        var expected = new double[] {1};
        genericTest(expected, Utils.merge(toOpen, F2_NAME));
    }

    @ParameterizedTest
    @MethodSource("openCloseOthers")
    public void sc6_testF1Open(String[] toOpen) {
        var expected = new double[] {0};
        genericTest(expected, Utils.merge(toOpen, F1_NAME));
    }



}
