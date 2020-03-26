package duc.sg.java.uncertainty;

public class Confidence {
    protected double probability;

    public Confidence(double probability) {
        setProbability(probability);
    }

    static void checkProb(double probability) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("Confidence should be between 0 and 1.");
        }
    }

    public void setProbability(double probability) {
        checkProb(probability);
        this.probability = probability;
    }


    public void setMaxProbability() {
        setProbability(1);
    }

    public double getProbability() {
        return probability;
    }

    public boolean probIsMax() {
        return probability == 1;
    }


    @Override
    public String toString() {
        return probability *100 + "%";
    }

}
