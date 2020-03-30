package duc.sg.java.uncertainty.math.test;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;

public class ConfidenceDataGenerator {
    private static final int NB_TESTS = 10;
    private static final Random RANDOM = new Random(87654);


    public static Arguments[] generateWrongProb() {
        var args = new Arguments[NB_TESTS];

        var half = (int)(NB_TESTS/2);
        for (int i = 0; i <half; i++) {
            args[i] = Arguments.of(RANDOM.nextDouble() - 1);
        }

        for (int i = half; i < NB_TESTS; i++) {
            args[i] = Arguments.of(RANDOM.nextDouble() + 1);
        }

        return args;


    }




}
