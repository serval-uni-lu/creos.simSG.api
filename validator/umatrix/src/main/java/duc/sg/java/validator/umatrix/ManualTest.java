package duc.sg.java.validator.umatrix;

import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;

public class ManualTest {

    public static void main(String[] args) {
        Scenario sc = new ScenarioBuilder()
                .chooseScenario(ScenarioName.CABINET)
                .build();

        var possMatrix = new PossibilityMatrix(sc.getSubstation());
        System.out.println(possMatrix);
    }
}
