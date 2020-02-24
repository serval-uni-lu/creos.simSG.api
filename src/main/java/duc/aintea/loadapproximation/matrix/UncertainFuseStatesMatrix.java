package duc.aintea.loadapproximation.matrix;

public class UncertainFuseStatesMatrix extends FuseStatesMatrix{
    private double confidence;

    public UncertainFuseStatesMatrix(FuseStatesMatrix fuseStatesMatrix, double confidence) {
        super(fuseStatesMatrix.data, fuseStatesMatrix.nbColumns, fuseStatesMatrix.reverse, fuseStatesMatrix.mapRowCable);
        this.confidence = confidence;
    }

    public double getConfidence() {
        return confidence;
    }
}
