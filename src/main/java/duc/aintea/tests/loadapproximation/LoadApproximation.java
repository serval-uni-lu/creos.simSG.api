package duc.aintea.tests.loadapproximation;

import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

public class LoadApproximation {
    protected DenseMatrix64F consumption;
    protected DenseMatrix64F fuseStates;

    public LoadApproximation(double[] consumptions, double[] matrix) {
        consumption = new DenseMatrix64F(consumptions.length, 1, true, consumptions);
        fuseStates = new DenseMatrix64F(matrix.length / consumptions.length, consumptions.length, true, matrix);
    }

   public double[] computeLoad() {
        DenseMatrix64F solution = new DenseMatrix64F(consumption.numRows, consumption.numCols);
        SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
        solver.setA(fuseStates);

        solver.solve(consumption, solution);

        return solution.data;
    }



}
