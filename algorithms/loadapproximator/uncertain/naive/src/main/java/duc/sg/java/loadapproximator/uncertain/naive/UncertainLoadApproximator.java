package duc.sg.java.loadapproximator.uncertain.naive;

import duc.sg.java.extracter.FuseExtractor;
import duc.sg.java.matrix.uncertain.UMatrixBuilder;
import duc.sg.java.matrix.uncertain.UEquationMatrix;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.uncertainty.PossibilityDouble;
import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

import java.util.HashSet;
import java.util.List;

public class UncertainLoadApproximator {
    private UncertainLoadApproximator() {}

    public static void approximate(final Substation substation) {
        List<UEquationMatrix> matrices = UMatrixBuilder.build(substation);
        var visited = new HashSet<Fuse>();

        for (UEquationMatrix usfm : matrices) {
            var fuseStates = new DenseMatrix64F(usfm.getNbColumns(), usfm.getNbColumns(), true, usfm.getValues());

            final var matConsumptions = new DenseMatrix64F(usfm.getNbColumns(), 1, true, usfm.getEqResults());

            DenseMatrix64F solution = new DenseMatrix64F(matConsumptions.numRows, matConsumptions.numCols);
            SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
            solver.setA(fuseStates);

            solver.solve(matConsumptions, solution);

            var solData = solution.data;
            var fuses = new HashSet<Fuse>(FuseExtractor.INSTANCE.getExtracted(substation));
            for (int i = 0; i < solData.length; i++) {
                Fuse current = usfm.getColumn(i);
                if (!visited.contains(current)) {
                    current.resetULoad();
                    visited.add(current);
                }

                var newPoss = new PossibilityDouble(solData[i], usfm.getConfidence());
                current.getUncertainLoad().addPossibility(newPoss);
                fuses.remove(current);
            }

            for(var f: fuses) {
                if (!visited.contains(f)) {
                    f.resetULoad();
                    visited.add(f);
                }
                var newPoss = new PossibilityDouble(0, usfm.getConfidence());
                f.getUncertainLoad().addPossibility(newPoss);
            }
        }

    }
}
