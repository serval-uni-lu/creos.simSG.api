package duc.sg.java.validator.umatrix;

import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;

public class ManualTest {

    public static void main(String[] args) {
        Scenario sc = new ScenarioBuilder()
//                .chooseScenario(ScenarioName.SINGLE_CABLE)
//                .chooseScenario(ScenarioName.CABINET)
//                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
//                .chooseScenario(ScenarioName.PARA_CABINET)
//                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .chooseScenario(ScenarioName.PARA_W_DEADEND)
                .build();
//
        var possMatrix = PossibilityMatrixBuilder1.build(sc.getSubstation());
        System.out.println(possMatrix);


    }
}
