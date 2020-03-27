package duc.sg.java.uncertainty;

public class Bernoulli extends Confidence {

    public Bernoulli(double probability) {
        super(probability);
    }

    public Bernoulli() {
        super(1);
    }

    public double getOppProbability() {
        return getOpposite(probability);
    }

    public void inverse() {
        setProbability(getOppProbability());
    }

    public static double getOpposite(double confidence) {
        checkProb(confidence);
        return 1 - confidence;
    }





}
