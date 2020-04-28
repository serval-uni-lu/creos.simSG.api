package duc.sg.java.matrix.uncertain;

import duc.sg.java.matrix.certain.FuseStatesMatrix;

public class UncertainFuseStatesMatrix extends FuseStatesMatrix {
    private double confidence;

    public UncertainFuseStatesMatrix(FuseStatesMatrix fuseStatesMatrix, double confidence) {
        super(fuseStatesMatrix.data, fuseStatesMatrix.nbColumns, fuseStatesMatrix.reverse, fuseStatesMatrix.mapRowCable);
        this.confidence = confidence;
    }

    public double getConfidence() {
        return confidence;
    }
}
