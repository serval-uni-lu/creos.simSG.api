package creos.simsg.api.preprocessor.powerflow;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.scenarios.Scenario;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;

import java.util.stream.Stream;

public class ManualTest {


    public static void main(String[] args) {
        Scenario sc = new ScenarioBuilder()
//                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .chooseScenario(ScenarioName.CABINET)
//                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
//                .chooseScenario(ScenarioName.PARA_CABINET)
//                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
//                .chooseScenario(ScenarioName.PARA_W_DEADEND)
                .build();

        Fuse[] toOPen2 = new PowerFlow2().getFuseOnMandatoryPF(sc.getSubstation());

        Stream.of(toOPen2)
                .map(Fuse::getName)
                .forEach(System.out::println);


    }
}
