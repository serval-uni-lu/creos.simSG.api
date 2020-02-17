package duc.aintea.loadapproximation.sg.scenarios;

import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.ParaCabinetBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ParaCabinetBuilderTest extends ExtractionTest {
    @Override
    protected Substation generateSubs(boolean[] fuseStates, double[] consumptions) {
        return ParaCabinetBuilder.build(fuseStates, consumptions);
    }

    @Override
    protected Fuse[] extractFuses() {
        return ParaCabinetBuilder.extractFuses(substation);
    }

    @Override
    protected Cable[] extractCables() {
        return ParaCabinetBuilder.extractCables(substation);
    }

    private static Arguments[] generateFuseStatus() {
        return Data.generateRandomArrBooleans(8);
    }

    private static Arguments[] generateConsumptions() {
        return Data.generateRandomArrDoubles(4);
    }

    @ParameterizedTest
    @MethodSource("generateFuseStatus")
    public void testExtractionFuses(boolean[] fuseStates) {
        var def_consumptions = new double[4];
        generic_testCableExtraction(fuseStates, def_consumptions);
    }

    @ParameterizedTest
    @MethodSource("generateConsumptions")
    public void testExtractionCables(double[] consumptions) {
        var def_fuseStates = new boolean[]{true, true, true, true, true, true, true, true};
        generic_testCableExtraction(def_fuseStates, consumptions);
    }

}
