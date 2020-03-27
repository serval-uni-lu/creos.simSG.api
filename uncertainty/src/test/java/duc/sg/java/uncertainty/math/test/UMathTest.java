package duc.sg.java.uncertainty.math.test;

import duc.sg.java.uncertainty.Confidence;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;
import duc.sg.java.uncertainty.math.UMath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class UMathTest {


    @ParameterizedTest
    @MethodSource("duc.sg.java.uncertainty.math.test.UMathDataGenerator#generateConfAndData")
    public void testConfidenceAnd(Confidence confA, Confidence confB, double expected) {
        var actual = UMath.and(confA, confB);
        assertNotNull(actual);
        assertEquals(expected, actual.getProbability());
    }

    @ParameterizedTest
    @MethodSource("duc.sg.java.uncertainty.math.test.UMathDataGenerator#generateConfOrData")
    public void testConfidenceOr(Confidence confA, Confidence confB, double expected) {
        var actual = UMath.or(confA, confB);
        assertNotNull(actual);
        assertEquals(expected, actual.getProbability());
    }

    @ParameterizedTest
    @MethodSource("duc.sg.java.uncertainty.math.test.UMathDataGenerator#generatePossDOrData")
    public void testPossDbleOr(PossibilityDouble a, PossibilityDouble b, double expected) {
        var actual = UMath.or(a, b);
        assertNotNull(actual);
        assertEquals(expected, actual.getConfidence().getProbability());

        var actualANull = UMath.or(null, b);
        assertEquals(b.getValue(), actualANull.getValue());
        assertEquals(b.getConfidence().getProbability(), actualANull.getConfidence().getProbability());

        var actualBNull = UMath.or(a, null);
        assertEquals(a.getValue(), actualBNull.getValue());
        assertEquals(a.getConfidence().getProbability(), actualBNull.getConfidence().getProbability());
    }


    @ParameterizedTest
    @MethodSource("duc.sg.java.uncertainty.math.test.UMathDataGenerator#generatePossDMaxData")
    public void testPossDMax(PossibilityDouble a, PossibilityDouble b, PossibilityDouble expt) {
        var actual = UMath.max(a, b);

        assertNotNull(actual);
        assertEquals(expt.getValue(), actual.getValue());
        assertEquals(expt.getConfidence().getProbability(), actual.getConfidence().getProbability());
    }

    @ParameterizedTest
    @MethodSource("duc.sg.java.uncertainty.math.test.UMathDataGenerator#generateMultPossDMaxData")
    public void testMultPossDMax(MultDblePossibilities a, MultDblePossibilities b, MultDblePossibilities expt) {
        var actual = UMath.max(a, b);

        Iterator<PossibilityDouble> itActual = actual.iterator();

        for (PossibilityDouble ex : expt) {
            assertTrue(itActual.hasNext());
            PossibilityDouble act = itActual.next();

            assertNotNull(act);
            assertEquals(ex.getValue(), act.getValue(), 0.0001);
            assertEquals(ex.getConfidence().getProbability(), act.getConfidence().getProbability(), 0.0001);
        }
        assertFalse(itActual.hasNext());
    }




}
