package creos.simsg.api.validator;

import creos.simsg.api.scenarios.ParaTransformerSC;
import creos.simsg.api.scenarios.Scenario;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Meter;
import creos.simsg.api.model.State;
import creos.simsg.api.scenarios.*;

import java.util.HashMap;
import java.util.Map;

public class ManualTest {

    public static Fuse get(Fuse[] array, String toSearch) {
        for(Fuse f: array) {
            if(f.getName().equals(toSearch)) {
                return f;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Scenario sc = new ScenarioBuilder()
//                .chooseScenario(ScenarioName.SINGLE_CABLE)
//                .chooseScenario(ScenarioName.CABINET)
                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
//                .chooseScenario(ScenarioName.PARA_CABINET)
//                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
//                .chooseScenario(ScenarioName.PARA_W_DEADEND)
                .build();
//
//        var possMatrix = PossibilityMatrixBuilder1.build(sc.getSubstation());
//        System.out.println(possMatrix);


        Map<Fuse, State> fuseStateMap = new HashMap<>();
        Fuse[] fuses = sc.extractFuses();

        for (int i = 0; i < fuses.length; i = i + 2) {
            var meter = new Meter("meter_" + i);
            meter.setConsumption(10. * (i+1));
            fuses[i].getCable().addMeters(meter);
        }

        // Single cable
//        fuseStateMap.put(get(fuses, SingleCableSC.F1_NAME), State.CLOSED);
//        fuseStateMap.put(get(fuses, SingleCableSC.F2_NAME), State.OPEN);

        // Cabinet
//        fuseStateMap.put(get(fuses, CabinetSC.F1_NAME), State.CLOSED);
//        fuseStateMap.put(get(fuses, CabinetSC.F2_NAME), State.CLOSED);
//        fuseStateMap.put(get(fuses, CabinetSC.F3_NAME), State.CLOSED);
//        fuseStateMap.put(get(fuses, CabinetSC.F4_NAME), State.CLOSED);
//        fuseStateMap.put(get(fuses, CabinetSC.F5_NAME), State.OPEN);
//        fuseStateMap.put(get(fuses, CabinetSC.F6_NAME), State.OPEN);

        // Para W Dead
//        fuseStateMap.put(get(fuses, ParaWithDeadendSC.F1_NAME), State.CLOSED);
//        fuseStateMap.put(get(fuses, ParaWithDeadendSC.F2_NAME), State.OPEN);
//        fuseStateMap.put(get(fuses, ParaWithDeadendSC.F3_NAME), State.CLOSED);
//        fuseStateMap.put(get(fuses, ParaWithDeadendSC.F4_NAME), State.CLOSED);

        // Para transformer
        fuseStateMap.put(get(fuses, ParaTransformerSC.F1_NAME), State.OPEN);
        fuseStateMap.put(get(fuses, ParaTransformerSC.F2_NAME), State.CLOSED);
        fuseStateMap.put(get(fuses, ParaTransformerSC.F3_NAME), State.CLOSED);
        fuseStateMap.put(get(fuses, ParaTransformerSC.F4_NAME), State.CLOSED);
        fuseStateMap.put(get(fuses, ParaTransformerSC.F5_NAME), State.CLOSED);
        fuseStateMap.put(get(fuses, ParaTransformerSC.F6_NAME), State.CLOSED);



        IValidator validator = new GridValidator();
        System.out.println(validator.isValid(sc.getSubstation(), fuseStateMap));


    }
}
