package creos.simsg.api.matrix.uncertain;

import creos.simsg.api.matrix.certain.EquationMatrixImp;

/**
 * Representation of the equation matrix considering the uncertainty of fuses. It adds a confidence level to the
 * matrix.
 */
public class UEquationMatrix extends EquationMatrixImp {
    private double confidence;

    public UEquationMatrix(EquationMatrixImp fuseStatesMatrix, double confidence) {
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
