package duc.sg.java.uncertainty.math.test;

import duc.sg.java.uncertainty.Confidence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class ConfidenceTest {


    @ParameterizedTest
    @MethodSource("duc.sg.java.uncertainty.math.test.ConfidenceDataGenerator#generateWrongProb")
    public void testWringProb(double val) {
        var conf = new Confidence(1);
        assertThrows(IllegalArgumentException.class, () -> conf.setProbability(val));
    }

    @Test
    public void testNearMax() {
        var conf = new Confidence(1);
        assertDoesNotThrow(() -> conf.setProbability(1.0001));
        assertEquals(Confidence.MAX_PROBABILITY, conf.getProbability());
        assertDoesNotThrow(() -> conf.setProbability(1.0009));
        assertEquals(Confidence.MAX_PROBABILITY, conf.getProbability());
        assertThrows(IllegalArgumentException.class, () -> conf.setProbability(1.001));
    }

    @Test
    public void testNearMin() {
        var conf = new Confidence(1);
        assertDoesNotThrow(() -> conf.setProbability(-0.0001));
        assertEquals(Confidence.MIN_PROBABILITY, conf.getProbability());
        assertDoesNotThrow(() -> conf.setProbability(-0.0009));
        assertEquals(Confidence.MIN_PROBABILITY, conf.getProbability());
        assertThrows(IllegalArgumentException.class, () -> conf.setProbability(-0.001));
    }

    @Test
    public void testMaxProb() {
        var conf = new Confidence(0.7);
        assertEquals(0.7, conf.getProbability());
        assertFalse(conf.probIsMax());
        conf.setMaxProbability();
        assertEquals(1, conf.getProbability());
        assertTrue(conf.probIsMax());
    }

}
