import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;
import duc.sg.java.scenarios.CabinetSC;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
public class ValidatorTest {

    @Test
    void LinkedSubstationTest() {

        //TODO validate the grid towards linked Substation rule
        SmartGrid grid = new ScenarioBuilder()
                    .chooseScenario(ScenarioName.LINKED_SUBSTATIONS)
                    .build()
                    .getGrid();


        Assertions.assertTrue(true);
    }

}
