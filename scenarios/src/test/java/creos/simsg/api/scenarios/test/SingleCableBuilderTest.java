package creos.simsg.api.scenarios.test;

import creos.simsg.api.model.State;
import creos.simsg.api.scenarios.Scenario;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SingleCableBuilderTest extends ExtractionTest {
    private static Arguments[] generateFuseStatus() {
        return TestHelper.generateRandomArrStatus(2);
    }

    private static Arguments[] generateConsumptions() {
        return TestHelper.generateRandomArrDoubles(1);
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
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .setFuseStates(fuseStates)
                .build();
    }

    @Override
    protected Scenario generateSubs(double[] consumptions) {
        return new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .setConsumptions(consumptions)
                .build();
    }
}
