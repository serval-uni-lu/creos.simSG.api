package duc.sg.java.scenarios.test;


import duc.sg.java.model.State;
import duc.sg.java.scenarios.Scenario;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ExtractionTest {
    protected Scenario scenario;


    protected abstract Scenario generateSubs(State[] fuseStates);
    protected abstract Scenario generateSubs(double[] consumptions);



    protected final void generic_testFuseExtraction(State[] fuseStates) {
        scenario = generateSubs(fuseStates);

        var extractionFuses = scenario.extractFuses();
        assertEquals(fuseStates.length, extractionFuses.length);

        for (int i = 0; i < extractionFuses.length; i++) {
            if(fuseStates[i] == State.CLOSED) {
                assertTrue(extractionFuses[i].isClosed(), "Fuse " + (i+1));
            } else {
                assertFalse(extractionFuses[i].isClosed(), "Fuse " + (i+1));
            }

        }
    }

    protected final void generic_testCableExtraction(double[] consumptions) {
        scenario = generateSubs(consumptions);

        var extractionCables = scenario.extractCables();
        assertEquals(consumptions.length, extractionCables.length);

        for (int i = 0; i < extractionCables.length; i++) {
            assertEquals(consumptions[i], extractionCables[i].getConsumption(), "Cable " + (i+1));
        }
    }


}
