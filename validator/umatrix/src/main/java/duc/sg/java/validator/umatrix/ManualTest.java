package duc.sg.java.validator.umatrix;

import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;

public class ManualTest {

    public static void main(String[] args) {
        Scenario sc = new ScenarioBuilder()
//                .chooseScenario(ScenarioName.SINGLE_CABLE)
//                .chooseScenario(ScenarioName.CABINET)
//                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
                .build();

        var possMatrix = new PossibilityMatrix(sc.getSubstation());
        System.out.println(possMatrix);
    }
}
