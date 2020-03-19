package duc.aintea.loadapproximation.test.sg;

import duc.aintea.sg.Cable;
import duc.aintea.sg.Extractor;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

public class ExtractorTest {

    private void genExtCableTest(Cable[] expected, List<Cable> actual) {
        var uniqueSet = new HashSet<>(actual);
        Assertions.assertEquals(expected.length, uniqueSet.size());
        for(Cable c: expected) {
            Assertions.assertTrue(uniqueSet.contains(c));
        }
    }

    private void genExtFusesTest(Fuse[] expected, List<Fuse> actual) {
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
        List<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseCab() {
        Substation substation = CabinetBuilder.build();
        Fuse[] expected = CabinetBuilder.extractFuses(substation);
        List<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseParaTrans() {
        Substation substation = ParaTransformerBuilder.build();
        Fuse[] expected = ParaTransformerBuilder.extractFuses(substation);
        List<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseParaCab() {
        Substation substation = ParaCabinetBuilder.build();
        Fuse[] expected = ParaCabinetBuilder.extractFuses(substation);
        List<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

    @Test
    public void testExtFuseIndPara() {
        Substation substation = IndirectParaBuilder.build();
        Fuse[] expected = IndirectParaBuilder.extractFuses(substation);
        List<Fuse> actual = Extractor.extractFuses(substation);
        genExtFusesTest(expected, actual);
    }

}
