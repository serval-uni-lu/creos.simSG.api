package duc.sg.java.loadapproximator.loadapproximation;

import duc.sg.java.loadapproximator.loadapproximation.matrix.FuseStatesMatrix;
import duc.sg.java.loadapproximator.loadapproximation.matrix.MatrixBuilder;
import duc.aintea.sg.Substation;
import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

public class LoadApproximator {
    private LoadApproximator(){}

    public static void approximate(final Substation substation) {
        FuseStatesMatrix matrix = MatrixBuilder.build(substation);

        var fuseStates = new DenseMatrix64F(matrix.getNbColumns(), matrix.getNbColumns(), true, matrix.getData());

        final var matConsumptions = new DenseMatrix64F(matrix.getNbColumns(), 1);
        var cblesOrder = matrix.getCables();
        for (int i = 0; i < cblesOrder.length; i++) {
            matConsumptions.set(i,0, cblesOrder[i].getConsumption());
        }


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
