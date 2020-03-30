package duc.sg.java.model.test;


import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.scenarios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

public class ExtractorTest {

    private void genExtCableTest(Scenario scenario, String subName) {
        Cable[] expected = scenario.extractCables();
        Collection<Cable> actual = scenario.getGrid()
                .getSubstation(subName)
                .get()
                .extractCables();

        var uniqueSet = new HashSet<>(actual);
        Assertions.assertEquals(expected.length, uniqueSet.size());
        for(Cable c: expected) {
            Assertions.assertTrue(uniqueSet.contains(c));
        }
    }

    private void genExtFusesTest(Scenario scenario, String subName) {
        Fuse[] expected = scenario.extractFuses();
        Collection<Fuse> actual = scenario.getGrid()
                .getSubstation(subName)
                .get()
                .extractFuses();

        var uniqueSet = new HashSet<>(actual);
        Assertions.assertEquals(expected.length, uniqueSet.size());
        for(Fuse f: expected) {
            Assertions.assertTrue(uniqueSet.contains(f));
        }
    }

    @Test
    public void testExtCableSingleCable() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .build();
        genExtCableTest(scenario, SingleCableSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtCableCab() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.CABINET)
                .build();
        genExtCableTest(scenario, CabinetSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtCableParaTrans() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
                .build();
        genExtCableTest(scenario, ParaTransformerSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtCableParaCab() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.PARA_CABINET)
                .build();
        genExtCableTest(scenario, ParaCabinetSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtCableIndPara() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .build();
        genExtCableTest(scenario, IndirectParaSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtFusesSingleCable() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .build();
        genExtFusesTest(scenario, SingleCableSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtFuseCab() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.CABINET)
                .build();
        genExtFusesTest(scenario, CabinetSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtFuseParaTrans() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.PARA_TRANSFORMER)
                .build();
        genExtFusesTest(scenario, ParaTransformerSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtFuseParaCab() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.PARA_CABINET)
                .build();
        genExtFusesTest(scenario, ParaCabinetSC.SUBSTATION_NAME);
    }

    @Test
    public void testExtFuseIndPara() {
        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(ScenarioName.INDIRECT_PARALLEL)
                .build();
        genExtFusesTest(scenario, IndirectParaSC.SUBSTATION_NAME);
    }

//    @Test
//    public void testRealCase() {
//        final String jsonPath = ExtractorTest.class
//                .getClassLoader()
//                .getResource("validJson/sg/realCase-1Subs.json")
//                .getPath();
//        final var file = new File(jsonPath);
//
//        final var subs = new Substation[1];
//        Assertions.assertDoesNotThrow(() -> {
//            Optional<List<Substation>> optionalSubstations = JsonImporter.from(file);
//            assertTrue(optionalSubstations.isPresent());
//
//            List<Substation> substations = optionalSubstations.get();
//            assertEquals(1, substations.size());
//            subs[0] = substations.get(0);
//        });
//
//        Collection<Fuse> fuses = Extractor.extractFuses(subs[0]);
//        List<Cable> cables = Extractor.extractCables(subs[0]);
//        assertEquals(62, fuses.size());
//        assertEquals(31, cables.size());
//    }

}
