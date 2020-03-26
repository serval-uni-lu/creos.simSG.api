package duc.sg.java.importer.json.test;

import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.importer.JsonImporter;
import duc.aintea.sg.scenarios.IndirectParaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JsonImporterTest {

    @ParameterizedTest
    @MethodSource("duc.aintea.loadapproximation.test.sg.importer.ScBasedJsonTest#getInvalidJsonFiles")
    public void testInvalidFile(File jsonFile) {
        assertDoesNotThrow(() -> {
            Optional<List<Substation>> substation = JsonImporter.from(jsonFile);
            assertTrue(substation.isEmpty());
        });
    }

    @Test
    public void testImportIndirectPara() {
        final String jsonPath = JsonImporterTest.class
                .getClassLoader()
                .getResource("validJson/sg/indirectPara.json")
                .getPath();
        final var file = new File(jsonPath);

        final var subs = new Substation[1];
        Assertions.assertDoesNotThrow(() -> {
            Optional<List<Substation>> optionalSubstations = JsonImporter.from(file);
            assertTrue(optionalSubstations.isPresent());

            List<Substation> substations = optionalSubstations.get();
            assertEquals(1, substations.size());
            subs[0] = substations.get(0);
        });


        Substation substation = subs[0];

        Fuse[] fuses = IndirectParaBuilder.extractFuses(substation);
        assertEquals("Fuse 1", fuses[0].getName());
        assertTrue(fuses[0].isClosed());
        assertEquals(0.7, fuses[0].getStatus().getConfClosedAsProb());

        assertEquals("Fuse 2", fuses[1].getName());
        assertTrue(fuses[1].isClosed());
        assertEquals(0.7, fuses[1].getStatus().getConfClosedAsProb());

        assertEquals("Fuse 3", fuses[2].getName());
        assertTrue(fuses[2].isClosed());
        assertEquals(0.7, fuses[2].getStatus().getConfClosedAsProb());

        assertEquals("Fuse 4", fuses[3].getName());
        assertFalse(fuses[3].isClosed());
        assertEquals(0.7, fuses[3].getStatus().getConfOpenAsProb());

        assertEquals("Fuse 5", fuses[4].getName());
        assertFalse(fuses[4].isClosed());
        assertEquals(0.7, fuses[4].getStatus().getConfOpenAsProb());

        assertEquals("Fuse 6", fuses[5].getName());
        assertFalse(fuses[5].isClosed());
        assertEquals(0.7, fuses[5].getStatus().getConfOpenAsProb());

        assertEquals("Fuse 7", fuses[6].getName());
        assertTrue(fuses[6].isClosed());
        assertEquals(0.7, fuses[6].getStatus().getConfClosedAsProb());

        assertEquals("Fuse 8", fuses[7].getName());
        assertTrue(fuses[7].isClosed());
        assertEquals(0.7, fuses[7].getStatus().getConfClosedAsProb());

        assertEquals("Fuse 9", fuses[8].getName());
        assertTrue(fuses[8].isClosed());
        assertEquals(0.7, fuses[8].getStatus().getConfClosedAsProb());

        assertEquals("Fuse 10", fuses[9].getName());
        assertTrue(fuses[9].isClosed());
        assertEquals(0.7, fuses[9].getStatus().getConfClosedAsProb());


        Cable[] cables = IndirectParaBuilder.extractCables(substation);
        assertEquals(30, cables[0].getConsumption());
        assertEquals(40, cables[1].getConsumption());
        assertEquals(50, cables[2].getConsumption());
        assertEquals(60, cables[3].getConsumption());
        assertEquals(70, cables[4].getConsumption());
    }


}
