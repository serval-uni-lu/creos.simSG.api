package duc.sg.java.uncertainty.math.test;

import duc.sg.java.uncertainty.Bernoulli;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;

class BernoulliDataGenerator {
    private static final int NB_TESTS = 10;
    private static final Random RANDOM = new Random(12345);



    static Arguments[] randomValues() {
        var res = new Arguments[NB_TESTS];
        for (int i = 0; i < res.length; i++) {
            var prob = RANDOM.nextDouble();
            res[i] = Arguments.of(new Bernoulli(prob), prob);
        }

        return res;
    }


}
