package duc.sg.java.scenarios.test;

import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ExtractionTest {
    protected Substation substation;


    protected abstract Substation generateSubs(boolean[] fuseStates, double[] consumptions);
    protected abstract Fuse[] extractFuses();
    protected abstract Cable[] extractCables();



    public void generic_testFuseExtraction(boolean[] fuseStates, double[] consumptions) {
        substation = generateSubs(fuseStates, consumptions);

        var extractionFuses = extractFuses();
        assertEquals(fuseStates.length, extractionFuses.length);

        for (int i = 0; i < extractionFuses.length; i++) {
            assertEquals(fuseStates[i], extractionFuses[i].isClosed(), "Fuse " + (i+1));
        }
    }

    public void generic_testCableExtraction(boolean[] fuseStates, double[] consumptions) {
        substation = generateSubs(fuseStates, consumptions);

        var extractionCables = extractCables();
        assertEquals(consumptions.length, extractionCables.length);

        for (int i = 0; i < extractionCables.length; i++) {
            assertEquals(consumptions[i], extractionCables[i].getConsumption(), "Cable " + (i+1));
        }
    }


}
