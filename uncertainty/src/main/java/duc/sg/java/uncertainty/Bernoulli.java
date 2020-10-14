package duc.sg.java.uncertainty;

/**
 * Representation of the <a href="https://en.wikipedia.org/wiki/Bernoulli_distribution">Bernoulli distribution</a>.
 */
public class Bernoulli extends Confidence {

    public Bernoulli(double probability) {
        super(probability);
    }

    public Bernoulli() {
        super(MAX_PROBABILITY);
    }

    public double getOppProbability() {
        return getOpposite(probability);
    }

    public boolean oppIsMax() {
        return getOppProbability() == MAX_PROBABILITY;
    }

    public void inverse() {
        setProbability(getOppProbability());
    }

    public static double getOpposite(double confidence) {
        checkProb(confidence);
        return MAX_PROBABILITY - confidence;
    }





}
