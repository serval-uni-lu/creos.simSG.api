package duc.aintea.loadapproximation.computation;

import duc.aintea.loadapproximation.LoadApproximator;
import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.sg.scenarios.CabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.aintea.sg.scenarios.CabinetBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CabinetTest extends LoadApproximatorTest {

    @Override
    protected void createSubstation() {
        substation = CabinetBuilder.build();
    }

    private static Arguments[] openCloseF5F6() {
       return Data.generateAllPossibilitiesWithValues(3, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseF3F4F5F6() {
        return Data.generateAllPossibilitiesWithValues(3, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }

    private static Arguments[] openCloseOthers() {
        return Data.generateAllPossibilitiesWithValues(3, F2_NAME, F3_NAME, F4_NAME, F5_NAME, F6_NAME);
    }




    private void test(String[] toOpen, Double[] consumptions) {
        openFuses(toOpen);
        initConsumptions(consumptions, F1_NAME, F3_NAME, F4_NAME);
        LoadApproximator.approximate(substation);

        var expectCbl1 = consumptions[0] + consumptions[1] + consumptions[2];
        var cbl1 = fusesMap.get(F1_NAME).getCable();
        assertEquals(expectCbl1, cbl1.getLoad(), DELTA);

        var cbl2 = fusesMap.get(F3_NAME).getCable();
        assertEquals(consumptions[1], cbl2.getLoad(), DELTA);

        var cbl3 = fusesMap.get(F4_NAME).getCable();
        assertEquals(consumptions[2], cbl3.getLoad(), DELTA);

        var f1 = fusesMap.get(F1_NAME);
        assertEquals(expectCbl1, f1.getLoad(), DELTA);

        var f2 = fusesMap.get(F2_NAME);
        assertEquals(-(consumptions[1] + consumptions[2]), f2.getLoad(), DELTA);

        var f3 = fusesMap.get(F3_NAME);
        assertEquals(consumptions[1], f3.getLoad(), DELTA);

        var f4 = fusesMap.get(F4_NAME);
        assertEquals(consumptions[2], f4.getLoad(), DELTA);

        var f5 = fusesMap.get(F5_NAME);
        assertEquals(0, f5.getLoad(), DELTA);

        var f6 = fusesMap.get(F6_NAME);
        assertEquals(0, f6.getLoad(), DELTA);

    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc1_testAllClosed(String[] toOpen, Double[] consumptions) {
        test(toOpen, consumptions);
    }


    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc2_testF4Open(String[] toOpen, Double[] consumptions) {
        consumptions[2] = 0.;
        test(toOpen, consumptions);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc3_testF3Open(String[] toOpen, Double[] consumptions) {
        consumptions[1] = 0.;
        test(toOpen, consumptions);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc4_testF3F4Open(String[] toOpen, Double[] consumptions) {
        consumptions[1] = 0.;
        consumptions[2] = 0.;
        test(toOpen, consumptions);
    }

    @ParameterizedTest
    @MethodSource("openCloseF3F4F5F6")
    public void sc5_testAllClosedButF1(String[] toOpen, Double[] consumptions) {
        consumptions[0] = 0.;
        consumptions[1] = 0.;
        consumptions[2] = 0.;
        test(toOpen, consumptions);
    }

    @ParameterizedTest
    @MethodSource("openCloseOthers")
    public void sc6_testAllOpen(String[] toOpen, Double[] consumptions) {
        consumptions[0] = 0.;
        consumptions[1] = 0.;
        consumptions[2] = 0.;
        test(toOpen, consumptions);
    }

}
