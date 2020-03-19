package duc.aintea.loadapproximation.test.sg;

import duc.aintea.sg.Cable;
import duc.aintea.sg.Extractor;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

public class ExtractorTest {

    private void genTest(Cable[] expected, List<Cable> actual) {
        var uniqueSet = new HashSet<>(actual);
        Assertions.assertEquals(expected.length, uniqueSet.size());
        for(Cable c: expected) {
            Assertions.assertTrue(uniqueSet.contains(c));
        }
    }

    @Test
    public void testSingleCable() {
        Substation substation = SingleCableBuilder.build();
        Cable[] expected = SingleCableBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
       genTest(expected, actual);
    }

    @Test
    public void testCab() {
        Substation substation = CabinetBuilder.build();
        Cable[] expected = CabinetBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genTest(expected, actual);
    }

    @Test
    public void testParaTrans() {
        Substation substation = ParaTransformerBuilder.build();
        Cable[] expected = ParaTransformerBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genTest(expected, actual);
    }

    @Test
    public void testParaCab() {
        Substation substation = ParaCabinetBuilder.build();
        Cable[] expected = ParaCabinetBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genTest(expected, actual);
    }

    @Test
    public void testIndPara() {
        Substation substation = IndirectParaBuilder.build();
        Cable[] expected = IndirectParaBuilder.extractCables(substation);
        List<Cable> actual = Extractor.extractCables(substation);
        genTest(expected, actual);
    }

}
