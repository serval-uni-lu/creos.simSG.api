package duc.sg.java.scenarios.test;

import duc.sg.java.model.State;
import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static duc.sg.java.scenarios.ScenarioName.CABINET;

public class CabinetBuilderTest extends ExtractionTest {
    private static Arguments[] generateFuseStatus() {
        return TestHelper.generateRandomArrStatus(6);
    }

    private static Arguments[] generateConsumptions() {
        return TestHelper.generateRandomArrDoubles(3);
    }

    @ParameterizedTest
    @MethodSource("generateFuseStatus")
    public void testExtractionFuses(State[] fuseStates) {
        generic_testFuseExtraction(fuseStates);
    }

    @ParameterizedTest
    @MethodSource("generateConsumptions")
    public void testExtractionCables(double[] consumptions) {
        generic_testCableExtraction(consumptions);
    }

    @Override
    protected Scenario generateSubs(State[] fuseStates) {
        return new ScenarioBuilder()
                .chooseScenario(CABINET)
                .setFuseStates(fuseStates)
                .build();
    }

    @Override
    protected Scenario generateSubs(double[] consumptions) {
        return new ScenarioBuilder()
                .chooseScenario(CABINET)
                .setConsumptions(consumptions)
                .build();
    }
}
