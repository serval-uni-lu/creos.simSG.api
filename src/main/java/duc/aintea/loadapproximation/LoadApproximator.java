package duc.aintea.loadapproximation;

import duc.aintea.loadapproximation.matrix.FuseStatesMatrix;
import duc.aintea.loadapproximation.matrix.MatrixBuilder;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

import java.util.Map;

public class LoadApproximator {

    public static void approximate(final Substation substation) {
        FuseStatesMatrix matrix = MatrixBuilder.build(substation);

        final var matConsumptions = new DenseMatrix64F(matrix.getNbColumns(), 1);
        var fuseStates = new DenseMatrix64F(matrix.getNbColumns(), matrix.getNbColumns(), true, matrix.getData());

        Map<Fuse, Integer> idxFuses = matrix.getIndexFuses();
        idxFuses.forEach((Fuse key, Integer idx) -> {
            matConsumptions.set((int) Math.round(idx/2.), 0, key.getCable().getConsumption());
        });

        DenseMatrix64F solution = new DenseMatrix64F(matConsumptions.numRows, matConsumptions.numCols);
        SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
        solver.setA(fuseStates);

        solver.solve(matConsumptions, solution);

        var solData = solution.data;
        for (int i = 0; i < solData.length; i++) {
            matrix.getFuse(i).setLoad(solData[i]);
        }
    }


}
