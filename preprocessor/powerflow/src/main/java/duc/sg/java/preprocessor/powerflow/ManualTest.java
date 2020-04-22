package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Fuse;
import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;

public class ManualTest {

    public static void main(String[] args) {
        Scenario sc = new ScenarioBuilder()
//                .chooseScenario(ScenarioName.SINGLE_CABLE)
//                .chooseScenario(ScenarioName.CABINET)
                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
//                .chooseScenario(ScenarioName.PARA_CABINET)
//                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .build();

//        InitPowerFlows.initPowerFlow(sc.getSubstation());
        InitPowerFlows2.initPowerFlow(sc.getSubstation());

        Fuse[] fuses = sc.extractFuses();
        for(var f: fuses) {
            var stringBldr = new StringBuilder();
            stringBldr.append(f.getName())
                    .append("-> [");

            Fuse[] powerFrom = f.getPowerFrom();
            for (int i = 0; i < powerFrom.length; i++) {
                stringBldr.append(powerFrom[i].getName());
                if(i < powerFrom.length - 1) {
                    stringBldr.append(", ");
                }
            }
            stringBldr.append("]");
            System.out.println(stringBldr.toString());
        }


    }
}
