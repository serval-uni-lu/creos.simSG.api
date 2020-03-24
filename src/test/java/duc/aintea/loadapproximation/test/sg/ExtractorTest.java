package duc.aintea.loadapproximation.test.sg;

import duc.aintea.loadapproximation.test.sg.importer.JsonImporterTest;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Extractor;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.importer.JsonImporter;
import duc.aintea.sg.scenarios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtractorTest {

    private void genExtCableTest(Cable[] expected, Collection<Cable> actual) {
        var uniqueSet = new HashSet<>(actual);
        Assertions.assertEquals(expected.length, uniqueSet.size());
        for(Cable c: expected) {
            Assertions.assertTrue(uniqueSet.contains(c));
        }
    }

    private void genExtFusesTest(Fuse[] expected, Collection<Fuse> actual) {
        var uniqueSet = new HashSet<>(actual);
        Assertions.assertEquals(expected.length, uniqueSet.size());
        for(Fuse f: expected) {
            Assertions.assertTrue(uniqueSet.contains(f));
        }
    }

    @Test
    public void testExtCableSingleCable() {
        Substation substation = SingleCableBuilder.build();
        Cable[] expected = SingleCableBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genExtCableTest(expected, actual);
    }

    @Test
    public void testExtCableCab() {
        Substation substation = CabinetBuilder.build();
        Cable[] expected = CabinetBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genExtCableTest(expected, actual);
    }

    @Test
    public void testExtCableParaTrans() {
        Substation substation = ParaTransformerBuilder.build();
        Cable[] expected = ParaTransformerBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genExtCableTest(expected, actual);
    }

    @Test
    public void testExtCableParaCab() {
        Substation substation = ParaCabinetBuilder.build();
        Cable[] expected = ParaCabinetBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genExtCableTest(expected, actual);
    }

    @Test
    public void testExtCableIndPara() {
        Substation substation = IndirectParaBuilder.build();
        Cable[] expected = IndirectParaBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genExtCableTest(expected, actual);
    }

    @Test
    public void testExtFusesSingleCable() {
        Substation substation = SingleCableBuilder.build();
        Fuse[] expected = SingleCableBuilder.extractFuses(substation);
        Collection<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseCab() {
        Substation substation = CabinetBuilder.build();
        Fuse[] expected = CabinetBuilder.extractFuses(substation);
        Collection<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseParaTrans() {
        Substation substation = ParaTransformerBuilder.build();
        Fuse[] expected = ParaTransformerBuilder.extractFuses(substation);
        Collection<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseParaCab() {
        Substation substation = ParaCabinetBuilder.build();
        Fuse[] expected = ParaCabinetBuilder.extractFuses(substation);
        Collection<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseIndPara() {
        Substation substation = IndirectParaBuilder.build();
        Fuse[] expected = IndirectParaBuilder.extractFuses(substation);
        Collection<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testRealCase() {
        final String jsonPath = JsonImporterTest.class
                .getClassLoader()
                .getResource("validJson/sg/realCase-1Subs.json")
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

        Collection<Fuse> fuses = Extractor.extractFuses(subs[0]);
        List<Cable> cables = Extractor.extractCables(subs[0]);
        assertEquals(62, fuses.size());
        assertEquals(31, cables.size());
    }

}
