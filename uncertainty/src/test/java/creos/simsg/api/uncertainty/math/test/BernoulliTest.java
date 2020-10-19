package creos.simsg.api.uncertainty.math.test;

import creos.simsg.api.uncertainty.Bernoulli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BernoulliTest {

    @ParameterizedTest
    @MethodSource("creos.simsg.api.uncertainty.math.test.BernoulliDataGenerator#randomValues")
    public void testOpposite(Bernoulli bernoulli, double prob)
    {
        assertEquals(prob, bernoulli.getProbability());
        assertEquals(1-prob, bernoulli.getOppProbability());

        bernoulli.inverse();
        assertEquals(prob, bernoulli.getOppProbability());
        assertEquals(1-prob, bernoulli.getProbability());

    }

    @Test
    public void testDefaultConstructor() {
        var bern = new Bernoulli();
        assertEquals(1, bern.getProbability());
        assertEquals(0, bern.getOppProbability());

    }



}
