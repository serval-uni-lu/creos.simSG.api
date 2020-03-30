package duc.sg.java.scenarios.test;

import duc.sg.java.model.State;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;

public class TestHelper {
    private static final double MAX_LOAD = 100.;
    private static final Random RANDOM = new Random(12345);
    static final int NB_TESTS = 10;

    public static Arguments[] generateRandomArrDoubles(int size) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object) random_double(size));
        }

        return res;
    }

    public static Arguments[] generateRandomArrStatus(int nb) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object) random_status(nb));
        }

        return res;
    }



    public static double randomValue(double max) {
        return RANDOM.nextDouble() * max;
    }


    private static double[] random_double(int nb) {
        var res = new double[nb];
        for (int j = 0; j < nb; j++) {
            res[j] = randomValue(MAX_LOAD);
        }
        return res;
    }


    private static State[] random_status(int nb) {
        var res = new State[nb];
        for (int j = 0; j < nb; j++) {
            if(RANDOM.nextBoolean()) {
                res[j] = State.CLOSED;
            } else {
                res[j] = State.OPEN;
            }

        }
        return res;
    }

}
