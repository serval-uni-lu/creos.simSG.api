package duc.aintea.loadapproximation;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;

class DataGenerator {
    private static final int NB_TESTS = 10;
    private static final double MAX_LOAD = 100.;
    private static final Random RANDOM = new Random(12345);



    private static double randomValue() {
        return RANDOM.nextDouble() * MAX_LOAD;
    }


    private static Arguments[] generate(int nb) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            Object[] values = new Double[nb];
            for (int j = 0; j < nb; j++) {
                values[j] = randomValue();
            }

            res[i] = Arguments.of(values);
        }

        return res;
    }

    public static Arguments[] oneValue() {
        return generate(1);
    }

    public static Arguments[] threeValues() {
        return generate(3);
    }

    public static Arguments[] fourValues() {
        return generate(4);
    }

    public static Arguments[] fiveValues() {
        return generate(5);
    }
}
