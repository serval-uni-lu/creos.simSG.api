package duc.sg.java.uncertainty;

/**
 * Class to represent an uncertain double with only one known value with its confidence.
 * It is used to represent when the confidence is only attached to one value. For example, an uncertain value
 * of 10.6 [84%] means that one knows that the value equals to 10.6 with 84% confidence. But, she does not know the
 * value with 26% confidence.
 */
public class  PossibilityDouble {
    private double value;
    private Confidence confidence;

    public PossibilityDouble(double value, Confidence confidence) {
        this.value = value;
        this.confidence = confidence;
    }

    public PossibilityDouble(double value, double confidence) {
        this(value, new Confidence(confidence));
    }

    public PossibilityDouble(PossibilityDouble other) {
        this(other.getValue(), other.getConfidence().getProbability());
    }

    public double getValue() {
        return value;
    }

    public Confidence getConfidence() {
        return confidence;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "(" + value + " [" + confidence + "])";
    }

}
