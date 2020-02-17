package duc.aintea.loadapproximation.sg.scenarios;

import duc.aintea.loadapproximation.generator.Data;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.IndirectParaBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IndirectParaBuilderTest extends ExtractionTest {
    private static Arguments[] generateFuseStatus() {
        return Data.generateRandomArrBooleans(10);
    }

    private static Arguments[] generateConsumptions() {
        return Data.generateRandomArrDoubles(5);
    }

    @ParameterizedTest
    @MethodSource("generateFuseStatus")
    public void testExtractionFuses(boolean[] fuseStates) {
        var def_consumptions = new double[5];
        generic_testFuseExtraction(fuseStates, def_consumptions);
    }

    @ParameterizedTest
    @MethodSource("generateConsumptions")
    public void testExtractionCables(double[] consumptions) {
        var def_fuseStates = new boolean[]{true, true, true, true, true, true, true, true, true, true};
        generic_testCableExtraction(def_fuseStates, consumptions);
    }

    @Override
    protected Substation generateSubs(boolean[] fuseStates, double[] consumptions) {
        return IndirectParaBuilder.build(fuseStates, consumptions);
    }

    @Override
    protected Fuse[] extractFuses() {
        return IndirectParaBuilder.extractFuses(substation);
    }

    @Override
    protected Cable[] extractCables() {
        return IndirectParaBuilder.extractCables(substation);
    }
}
