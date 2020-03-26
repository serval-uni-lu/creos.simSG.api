package duc.sg.java.uncertainty;

public class PossibilityDouble {
    private double value;
    private Confidence confidence;

    public PossibilityDouble(double value, Confidence confidence) {
        this.value = value;
        this.confidence = confidence;
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
