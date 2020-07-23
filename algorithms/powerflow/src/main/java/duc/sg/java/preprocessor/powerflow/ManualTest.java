package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Fuse;
import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;

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

        Fuse[] toOPen = new PowerFlow().getFuseOnMandatoryPF(sc.getSubstation());

        Fuse[] toOPen2 = new PowerFlow2().getFuseOnMandatoryPF(sc.getSubstation());

        Stream.of(toOPen)
                .map(Fuse::getName)
                .forEach(System.out::println);

        System.out.println();

        Stream.of(toOPen2)
                .map(Fuse::getName)
                .forEach(System.out::println);


    }
}
