package duc.aintea.loadapproximation;

import duc.aintea.loadapproximation.matrix.FuseStatesMatrix;
import duc.aintea.loadapproximation.matrix.UncertainMatrixBuilder;
import duc.aintea.sg.Substation;

public class UncertainLoadApproximator {

    public static void approximate(final Substation substation) {
        FuseStatesMatrix[] matrices = UncertainMatrixBuilder.build(substation);

    }
}
