package creos.simsg.api.uncertainty;

/**
 * Class to manipulate a confidence level: a value between 0 and 1.
 */
public class Confidence {
    protected double probability;
    public static final int MAX_PROBABILITY = 1;
    public static final int MIN_PROBABILITY = 0;

    public Confidence(double probability) {
        setProbability(probability);
    }

    static void checkProb(double probability) {
        if (probability < MIN_PROBABILITY || probability > MAX_PROBABILITY) {
            throw new IllegalArgumentException("Confidence should be between 0 and 1.");
        }
    }

    public void setProbability(double probability) {
        checkProb(probability);
        this.probability = probability;
    }


    public void setMaxProbability() {
        setProbability(MAX_PROBABILITY);
    }

    public double getProbability() {
        return probability;
    }

    public boolean probIsMax() {
        return probability == MAX_PROBABILITY;
    }


    @Override
    public String toString() {
        return probability *100 + "%";
    }

}
