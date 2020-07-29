package duc.sg.java.matrix.uncertain;

import duc.sg.java.matrix.certain.CertainFuseStateMatrix;

public class UStatesMatrix extends CertainFuseStateMatrix {
    private double confidence;

    public UStatesMatrix(CertainFuseStateMatrix fuseStatesMatrix, double confidence) {
        super(fuseStatesMatrix);
        this.confidence = confidence;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
