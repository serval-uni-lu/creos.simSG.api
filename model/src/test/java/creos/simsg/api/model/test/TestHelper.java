package creos.simsg.api.model.test;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;

class TestHelper {
    private static final int NB_TESTS = 10;
    private static final Random RANDOM = new Random(12345);

    private static double randomValue(double max) {
        return RANDOM.nextDouble() * max;
    }


    public static Arguments[] generateRandomDoubles(double max) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of(randomValue(max));
        }

        return res;
    }


}
