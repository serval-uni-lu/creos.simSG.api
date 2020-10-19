package creos.simsg.api.transformer.json.importer.test;

import creos.simsg.api.model.Cable;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.SmartGrid;
import creos.simsg.api.scenarios.IndirectParaSC;
import creos.simsg.api.scenarios.Scenario;
import creos.simsg.api.scenarios.ScenarioBuilder;
import creos.simsg.api.scenarios.ScenarioName;
import creos.simsg.api.transformer.json.importer.JsonGridImporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JsonGridImporterTest {

    @ParameterizedTest
    @MethodSource("creos.simsg.api.transformer.json.importer.test.JsonSCImporterTest#getInvalidJsonFiles")
    public void testInvalidFile(File jsonFile) {
        assertDoesNotThrow(() -> {
            Optional<SmartGrid> substation = JsonGridImporter.INSTANCE.from(jsonFile);
            assertTrue(substation.isEmpty());
        });
    }

    @Test
    public void testImportIndirectPara() {
        final String jsonPath = JsonGridImporterTest.class
                .getClassLoader()
                .getResource("validJson/sg/indirectPara.json")
                .getPath();
        final var file = new File(jsonPath);

        final var sgs = new SmartGrid[1];
        Assertions.assertDoesNotThrow(() -> {
            Optional<SmartGrid> optionalSubstations = JsonGridImporter.INSTANCE.from(file);
            assertTrue(optionalSubstations.isPresent());
            SmartGrid grid = optionalSubstations.get();
            sgs[0] = grid;
        });

        SmartGrid grid = sgs[0];
        assertTrue(grid.getSubstation(IndirectParaSC.SUBSTATION_NAME).isPresent());
        Scenario scenario = new ScenarioBuilder().chooseScenario(ScenarioName.INDIRECT_PARALLEL).wrap(grid).build();

        Fuse[] fuses = scenario.extractFuses();
        assertEquals("Fuse 1", fuses[0].getName());
        assertTrue(fuses[0].isClosed());
        assertEquals(0.7, fuses[0].getStatus().confIsClosed());

        assertEquals("Fuse 2", fuses[1].getName());
        assertTrue(fuses[1].isClosed());
        assertEquals(0.7, fuses[1].getStatus().confIsClosed());

        assertEquals("Fuse 3", fuses[2].getName());
        assertTrue(fuses[2].isClosed());
        assertEquals(0.7, fuses[2].getStatus().confIsClosed());

        assertEquals("Fuse 4", fuses[3].getName());
        assertFalse(fuses[3].isClosed());
        assertEquals(0.7, fuses[3].getStatus().confIsOpen());

        assertEquals("Fuse 5", fuses[4].getName());
        assertFalse(fuses[4].isClosed());
        assertEquals(0.7, fuses[4].getStatus().confIsOpen());

        assertEquals("Fuse 6", fuses[5].getName());
        assertFalse(fuses[5].isClosed());
        assertEquals(0.7, fuses[5].getStatus().confIsOpen());

        assertEquals("Fuse 7", fuses[6].getName());
        assertTrue(fuses[6].isClosed());
        assertEquals(0.7, fuses[6].getStatus().confIsClosed());

        assertEquals("Fuse 8", fuses[7].getName());
        assertTrue(fuses[7].isClosed());
        assertEquals(0.7, fuses[7].getStatus().confIsClosed());

        assertEquals("Fuse 9", fuses[8].getName());
        assertTrue(fuses[8].isClosed());
        assertEquals(0.7, fuses[8].getStatus().confIsClosed());

        assertEquals("Fuse 10", fuses[9].getName());
        assertTrue(fuses[9].isClosed());
        assertEquals(0.7, fuses[9].getStatus().confIsClosed());


        Cable[] cables = scenario.extractCables();
        assertEquals(30, cables[0].getConsumption());
        assertEquals(40, cables[1].getConsumption());
        assertEquals(50, cables[2].getConsumption());
        assertEquals(60, cables[3].getConsumption());
        assertEquals(70, cables[4].getConsumption());
    }


}
