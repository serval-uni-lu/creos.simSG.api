package creos.simsg.api.validator.test;

import creos.simsg.api.extractor.FuseExtractor;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.SmartGrid;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import creos.simsg.api.validator.rules.IRule;
import creos.simsg.api.validator.rules.LinkedSubstationRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValidatorTest {

    @Test
    void LinkedSubstationTest() {

        //TODO validate the grid towards linked Substation rule
        SmartGrid grid = new ScenarioBuilder()
                    .chooseScenario(ScenarioName.LINKED_SUBSTATIONS)
                    .build()
                    .getGrid();


        Substation substation = new ArrayList<>(grid.getSubstations()).get(0);
        Map<Fuse, State> fuseStateMap = new HashMap<>();
        FuseExtractor.INSTANCE.getExtracted(substation).forEach(f -> fuseStateMap.put(f,f.getStatus().getState()));

        IRule linkedsubstationRule = new LinkedSubstationRule();
        Assertions.assertTrue(linkedsubstationRule.apply(substation,fuseStateMap));
    }

}
