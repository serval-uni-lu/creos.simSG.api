package duc.aintea.loadapproximation.computation;

import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.loadapproximation.generator.Utils;
import duc.aintea.sg.scenarios.ParaCabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.sg.scenarios.ParaCabinetBuilder.*;


public class ParaCabinetTest extends LoadApproximatorTest {
    @Override
    protected void createSubstation() {
        substation = ParaCabinetBuilder.build();
    }

    @Override
    protected String[] getFuses() {
        return new String[] {F1_NAME, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME, F7_NAME, F8_NAME};
    }

    @Override
    protected String[] getFuseCables() {
        return new String[]{F1_NAME, F3_NAME, F5_NAME, F7_NAME};
    }


    private static Arguments[] openCloseF8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF2F3F4F5F6F7F8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F2_NAME, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F5_NAME, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6F7F8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F3_NAME, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F5_NAME, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF4F7F8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF4F6F7F8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF6F7F8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F6_NAME, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF7F8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F7_NAME, ParaCabinetBuilder.F8_NAME);
    }

    private static Arguments[] openCloseF4F8() {
        return Data.generateAllPossibilitiesWithValues(4, ParaCabinetBuilder.F4_NAME, ParaCabinetBuilder.F8_NAME);
    }


    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc1_allClosed(String[] toOpen, Double[] consumptions) {
        double sumPara = consumptions[1] + consumptions[2] + consumptions[3];
        double half = sumPara/2;
        var expectedCables = new double[] {
                consumptions[0] + sumPara,
                half,
                half,
                consumptions[3]
        };
        var expectedFuses = new double[] {
                consumptions[0] + sumPara,
                -sumPara,
                half,
                consumptions[1] - half,
                half,
                consumptions[2] - half,
                consumptions[3],
                0
        };
        genericTest(toOpen, consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F3F4F5F6F7F8")
    public void sc2_f1Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {0,0,0,0};
        var expectedFuses = new double[] {0,0,0,0,0,0,0,0};
        genericTest(Utils.merge(toOpen, F1_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6F7F8")
    public void sc3_f2Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {consumptions[0],0,0,0};
        var expectedFuses = new double[] {consumptions[0],0,0,0,0,0,0,0};
        genericTest(Utils.merge(toOpen, F2_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F6F7F8")
    public void sc4_f3f5Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {consumptions[0],0,0,0};
        var expectedFuses = new double[] {consumptions[0],0,0,0,0,0,0,0};
        genericTest(Utils.merge(toOpen, F3_NAME, F5_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF7F8")
    public void sc5_f4f6Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2],
                consumptions[1],
                consumptions[2],
                0
        };
        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2],
                -consumptions[1] - consumptions[2],
                consumptions[1],
                0,
                consumptions[2],
                0,
                0,
                0};
        genericTest(Utils.merge(toOpen, F4_NAME, F6_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc6_f7Open(String[] toOpen, Double[] consumptions) {
        var para = consumptions[1] + consumptions[2];
        var half = para / 2;
        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2],
                -para,
                half,
                consumptions[1] - half,
                half,
                consumptions[2] - half,
                0,
                0
        };

        var expectedCables = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2],
                Math.max(expectedFuses[2], expectedFuses[3]),
                Math.max(expectedFuses[4], expectedFuses[5]),
                0
        };
        genericTest(Utils.merge(toOpen, F7_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc7_f6Open(String[] toOpen, Double[] consumptions) {
        var sum =  consumptions[0] + consumptions[1] + consumptions[2] + consumptions[3];
        var expectedCables = new double[] {
                sum,
                consumptions[1] + consumptions[3],
                consumptions[2],
                consumptions[3]
        };

        var expectedFuses = new double[] {
                sum,
                -consumptions[1] - consumptions[2] - consumptions[3],
                consumptions[1] + consumptions[3],
                -consumptions[3],
                consumptions[2],
                0,
                consumptions[3],
                0
        };
        genericTest(Utils.merge(toOpen, F6_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc8_f6f7Open(String[] toOpen, Double[] consumptions) {
        var sum =  consumptions[0] + consumptions[1] + consumptions[2];
        var expectedCables = new double[] {
                sum,
                consumptions[1],
                consumptions[2],
                0
        };

        var expectedFuses = new double[] {
                sum,
                -consumptions[1] - consumptions[2],
                consumptions[1],
                0,
                consumptions[2],
                0,
                0,
                0
        };
        genericTest(Utils.merge(toOpen, F6_NAME, F7_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc9_f5Open(String[] toOpen, Double[] consumptions){
        var sum =  consumptions[0] + consumptions[1] + consumptions[2] + consumptions[3];
        var expectedCables = new double[] {
                sum,
                sum - consumptions[0],
                consumptions[2],
                consumptions[3]
        };

        var expectedFuses = new double[] {
                sum,
                consumptions[0] - sum,
                sum - consumptions[0],
                -consumptions[2] - consumptions[3],
                0,
                consumptions[2],
                consumptions[3],
                0
        };
        genericTest(Utils.merge(toOpen, F5_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc10_f5f7Open(String[] toOpen, Double[] consumptions){
        var sum =  consumptions[0] + consumptions[1] + consumptions[2];
        var expectedCables = new double[] {
                sum,
                sum - consumptions[0],
                consumptions[2],
                0
        };

        var expectedFuses = new double[] {
                sum,
                consumptions[0] - sum,
                sum - consumptions[0],
                -consumptions[2],
                0,
                consumptions[2],
                0,
                0
        };
        genericTest(Utils.merge(toOpen, F5_NAME, F7_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc11_f5f6Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1] + consumptions[3],
                consumptions[1] + consumptions[3],
                0,
                consumptions[3]
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1] + consumptions[3],
                -consumptions[1] - consumptions[3],
                consumptions[1] + consumptions[3],
                -consumptions[3],
                0,
                0,
                consumptions[3],
                0
        };
        genericTest(Utils.merge(toOpen, F5_NAME, F6_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F8")
    public void sc12_f5f6f7Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1],
                consumptions[1],
                0,
                0
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1],
                -consumptions[1],
                consumptions[1],
                0,
                0,
                0,
                0,
                0
        };
        genericTest(Utils.merge(toOpen, F5_NAME, F6_NAME, F7_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc13_f4Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2] + consumptions[3],
                consumptions[1],
                consumptions[2] + consumptions[3],
                consumptions[3]
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2] + consumptions[3],
                -consumptions[1] - consumptions[2] - consumptions[3],
                consumptions[1],
                0,
                consumptions[2] + consumptions[3],
                -consumptions[3],
                consumptions[3],
                0
        };
        genericTest(Utils.merge(toOpen, F4_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6F7F8")
    public void sc14_f4f5Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1],
                consumptions[1],
                0,
                0
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1],
                -consumptions[1],
                consumptions[1],
                0,
                0,
                0,
                0,
                0
        };
        genericTest(Utils.merge(toOpen, F5_NAME, F4_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F7F8")
    public void sc15_f3f6Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[2],
                0,
                consumptions[2],
                0
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[2],
                -consumptions[2],
                0,
                0,
                consumptions[2],
                0,
                0,
                0
        };
        genericTest(Utils.merge(toOpen, F3_NAME, F6_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc16_f3f7Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2],
                consumptions[1],
                consumptions[1] + consumptions[2],
                0
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2],
                -consumptions[1] - consumptions[2],
                0,
                consumptions[1],
                consumptions[1] + consumptions[2],
                -consumptions[1],
                0,
                0

        };
        genericTest(Utils.merge(toOpen, F3_NAME, F7_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc17_f3Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2] + consumptions[3],
                consumptions[1],
                consumptions[1] + consumptions[2] + consumptions[3],
                consumptions[3]
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[1] + consumptions[2] + consumptions[3],
                -consumptions[1] - consumptions[2] - consumptions[3],
                0,
                consumptions[1],
                consumptions[1] + consumptions[2] + consumptions[3],
                -consumptions[1] -  consumptions[3],
                consumptions[3],
                0

        };
        genericTest(Utils.merge(toOpen, F3_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc18_f3f4Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[2] + consumptions[3],
                0,
                consumptions[2] + consumptions[3],
                consumptions[3]
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[2] + consumptions[3],
                -consumptions[2] - consumptions[3],
                0,
                0,
                consumptions[2] + consumptions[3],
                -consumptions[3],
                consumptions[3],
                0
        };
        genericTest(Utils.merge(toOpen, F3_NAME, F4_NAME), consumptions, expectedCables, expectedFuses);
    }

    @ParameterizedTest
    @MethodSource("openCloseF8")
    public void sc19_f3f4f4f7Open(String[] toOpen, Double[] consumptions) {
        var expectedCables = new double[] {
                consumptions[0] + consumptions[2],
                0,
                consumptions[2],
                0
        };

        var expectedFuses = new double[] {
                consumptions[0] + consumptions[2],
                -consumptions[2],
                0,
                0,
                consumptions[2],
                0,
                0,
                0
        };
        genericTest(Utils.merge(toOpen, F3_NAME, F4_NAME, F7_NAME), consumptions, expectedCables, expectedFuses);
    }


}
