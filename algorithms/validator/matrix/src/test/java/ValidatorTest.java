import duc.sg.java.extractor.FuseExtracter;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import duc.sg.java.validator.rules.IRule;
import duc.sg.java.validator.rules.LinkedSubstationRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

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
        FuseExtracter.INSTANCE.getExtracted(substation).forEach(f -> fuseStateMap.put(f,f.getStatus().getState()));

        IRule linkedsubstationRule = new LinkedSubstationRule();
        Assertions.assertTrue(linkedsubstationRule.apply(substation,fuseStateMap));
    }

}
