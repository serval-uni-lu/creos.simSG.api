package duc.sg.java.uncertainty.math.test;

import duc.sg.java.uncertainty.Confidence;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;
import duc.sg.java.uncertainty.math.UMath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class UMathTest {
    private static final int NB_TESTS = 10;
    private static final Random RANDOM = new Random(12345);

    private static Arguments[] generateConfAndData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            Confidence a = randConfidence();
            Confidence b = randConfidence();
            res[i] = Arguments.of(a, b, a.getProbability()*b.getProbability());
        }

        return res;
    }

    private static Arguments[] generateConfOrData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            Confidence a = randConfidence();
            Confidence b = randConfidence();
            res[i] = Arguments.of(a, b, a.getProbability()+b.getProbability());
        }

        return res;
    }

    private static Arguments[] generatePossDOrData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            double value = randValue();
            var possA = new PossibilityDouble(value, randConfidence());
            var possB = new PossibilityDouble(value, randConfidence());
            res[i] = Arguments.of(possA, possB, possA.getConfidence().getProbability() + possB.getConfidence().getProbability());
        }

        return res;
    }

    private static double randValue() {
        int sign = (RANDOM.nextBoolean()? 1 : -1);
        return RANDOM.nextDouble() * Double.MAX_VALUE * sign;
    }

    private static Confidence randConfidence() {
        return new Confidence(RANDOM.nextDouble() * 0.5);
    }

    private static Arguments[] generatePossDMaxData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            var a = new PossibilityDouble(randValue(), randConfidence());
            var b = new PossibilityDouble(randValue(), randConfidence());
            var c = new PossibilityDouble(
                    Math.max(a.getValue(),
                            b.getValue()),
                    new Confidence(a.getConfidence().getProbability()*b.getConfidence().getProbability())
            );

            res[i] = Arguments.of(a, b, c);
        }

        return res;
    }

    private static Arguments[] generateMultPossDMaxData() {
        var res = new Arguments[1];

        var multA = new MultDblePossibilities();
        multA.add(new PossibilityDouble(0, new Confidence(0.1)));
        multA.add(new PossibilityDouble(20, new Confidence(0.6)));
        multA.add(new PossibilityDouble(50, new Confidence(0.3)));

        var multB = new MultDblePossibilities();
        multB.add(new PossibilityDouble(15, new Confidence(0.1)));
        multB.add(new PossibilityDouble(20, new Confidence(0.5)));
        multB.add(new PossibilityDouble(50, new Confidence(0.4)));

        var expt = new MultDblePossibilities();
        expt.add(new PossibilityDouble(15, new Confidence(0.01)));
        expt.add(new PossibilityDouble(20, new Confidence(0.41)));
        expt.add(new PossibilityDouble(50, new Confidence(0.58)));

        res[0] = Arguments.of(multA, multB, expt);

        return res;
    }

    @ParameterizedTest
    @MethodSource("generateConfAndData")
    public void testConfidenceAnd(Confidence confA, Confidence confB, double expected) {
        var actual = UMath.and(confA, confB);
        assertNotNull(actual);
        assertEquals(expected, actual.getProbability());
    }

    @ParameterizedTest
    @MethodSource("generateConfOrData")
    public void testConfidenceOr(Confidence confA, Confidence confB, double expected) {
        var actual = UMath.or(confA, confB);
        assertNotNull(actual);
        assertEquals(expected, actual.getProbability());
    }

    @ParameterizedTest
    @MethodSource("generatePossDOrData")
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
    @MethodSource("generatePossDMaxData")
    public void testPossDMax(PossibilityDouble a, PossibilityDouble b, PossibilityDouble expt) {
        var actual = UMath.max(a, b);

        assertNotNull(actual);
        assertEquals(expt.getValue(), actual.getValue());
        assertEquals(expt.getConfidence().getProbability(), actual.getConfidence().getProbability());
    }

    @ParameterizedTest
    @MethodSource("generateMultPossDMaxData")
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
