package duc.sg.java.uncertainty.math.test;

import duc.sg.java.uncertainty.Confidence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class ConfidenceTest {

    @ParameterizedTest
    @MethodSource("duc.sg.java.uncertainty.math.test.ConfidenceDataGenerator#generateWrongProb")
    public void testWrongProb(double val) {
        var conf = new Confidence(1);
        assertThrows(IllegalArgumentException.class, () -> conf.setProbability(val));
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
