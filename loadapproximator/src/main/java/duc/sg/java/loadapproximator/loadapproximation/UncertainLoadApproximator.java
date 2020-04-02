package duc.sg.java.loadapproximator.loadapproximation;

import duc.sg.java.loadapproximator.loadapproximation.matrix.UncertainFuseStatesMatrix;
import duc.sg.java.loadapproximator.loadapproximation.matrix.UncertainMatrixBuilder;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.uncertainty.PossibilityDouble;
import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

import java.util.HashSet;

public class UncertainLoadApproximator {
    private UncertainLoadApproximator() {}

    public static void approximate(final Substation substation) {
        UncertainFuseStatesMatrix[] matrices = UncertainMatrixBuilder.build(substation);
        var visited = new HashSet<Fuse>();

        for (UncertainFuseStatesMatrix usfm : matrices) {
            var fuseStates = new DenseMatrix64F(usfm.getNbColumns(), usfm.getNbColumns(), true, usfm.getData());

            final var matConsumptions = new DenseMatrix64F(usfm.getNbColumns(), 1);
            var cblesOrder = usfm.getCables();
            for (int i = 0; i < cblesOrder.length; i++) {
                matConsumptions.set(i, 0, cblesOrder[i].getConsumption());
            }


            DenseMatrix64F solution = new DenseMatrix64F(matConsumptions.numRows, matConsumptions.numCols);
            SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
            solver.setA(fuseStates);

            solver.solve(matConsumptions, solution);

            var solData = solution.data;
            for (int i = 0; i < solData.length; i++) {
                Fuse current = usfm.getFuse(i);
                if (!visited.contains(current)) {
                    current.resetULoad();
                    visited.add(current);
                }

                var newPoss = new PossibilityDouble(solData[i], usfm.getConfidence());
                current.getUncertainLoad().add(newPoss);
            }
        }

    }
}
