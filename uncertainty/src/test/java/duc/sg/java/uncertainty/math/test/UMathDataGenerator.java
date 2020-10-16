package duc.sg.java.uncertainty.math.test;

import duc.sg.java.uncertainty.Confidence;
import duc.sg.java.uncertainty.MultiplePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;

class UMathDataGenerator {
    private static final int NB_TESTS = 10;
    private static final Random RANDOM = new Random(12345);

    private static double randValue() {
        int sign = (RANDOM.nextBoolean() ? 1 : -1);
        return RANDOM.nextDouble() * Double.MAX_VALUE * sign;
    }

    private static Confidence randConfidence() {
        return new Confidence(RANDOM.nextDouble() * 0.5);
    }

    public static Arguments[] generateConfAndData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            Confidence a = randConfidence();
            Confidence b = randConfidence();
            double expected = a.getProbability() * b.getProbability();
//            if(Math.abs(expected - Confidence.MIN_PROBABILITY) <= Confidence.PRECISION) expected = Confidence.MIN_PROBABILITY;
//            else if(Math.abs(expected - Confidence.MAX_PROBABILITY) <= Confidence.PRECISION) expected = Confidence.MAX_PROBABILITY;
            res[i] = Arguments.of(a, b, expected);
        }

        return res;
    }

    public static Arguments[] generateConfOrData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            Confidence a = randConfidence();
            Confidence b = randConfidence();
            res[i] = Arguments.of(a, b, a.getProbability() + b.getProbability());
        }

        return res;
    }

    public static Arguments[] generatePossDOrData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            double value = randValue();
            var possA = new PossibilityDouble(value, randConfidence());
            var possB = new PossibilityDouble(value, randConfidence());
            res[i] = Arguments.of(possA, possB, possA.getConfidence().getProbability() + possB.getConfidence().getProbability());
        }

        return res;
    }


    public static Arguments[] generatePossDMaxData() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            var a = new PossibilityDouble(randValue(), randConfidence());
            var b = new PossibilityDouble(randValue(), randConfidence());
            var c = new PossibilityDouble(
                    Math.max(a.getValue(),
                            b.getValue()),
                    new Confidence(a.getConfidence().getProbability() * b.getConfidence().getProbability())
            );

            res[i] = Arguments.of(a, b, c);
        }

        return res;
    }

    public static Arguments[] generateMultPossDMaxData() {
        var res = new Arguments[1];

        var multA = new MultiplePossibilities();
        multA.addPossibility(new PossibilityDouble(0, new Confidence(0.1)));
        multA.addPossibility(new PossibilityDouble(20, new Confidence(0.6)));
        multA.addPossibility(new PossibilityDouble(50, new Confidence(0.3)));

        var multB = new MultiplePossibilities();
        multB.addPossibility(new PossibilityDouble(15, new Confidence(0.1)));
        multB.addPossibility(new PossibilityDouble(20, new Confidence(0.5)));
        multB.addPossibility(new PossibilityDouble(50, new Confidence(0.4)));

        var expt = new MultiplePossibilities();
        expt.addPossibility(new PossibilityDouble(15, new Confidence(0.01)));
        expt.addPossibility(new PossibilityDouble(20, new Confidence(0.41)));
        expt.addPossibility(new PossibilityDouble(50, new Confidence(0.58)));

        res[0] = Arguments.of(multA, multB, expt);

        return res;
    }
}
