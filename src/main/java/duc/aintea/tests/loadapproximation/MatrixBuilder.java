package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Substation;
import org.ejml.data.DenseMatrix64F;

public class MatrixBuilder {

    public double[] build(Substation substation) {
        var cableEq = new DenseMatrix64F(0);
        var deadEndsEq = new DenseMatrix64F(0);

        var neighbors = substation.getReachableEntities();
        cableEq.reshape(neighbors.size(), neighbors.size() * 2);

        for (int i = 0; i < neighbors.size(); i++) {
            cableEq.add(i, 0, 1);
            cableEq.add(i, 1, 1);

            if(neighbors.get(i).isDeadEnd()) {
                deadEndsEq.reshape(deadEndsEq.numRows + 1, cableEq.numCols, true);
                deadEndsEq.add(deadEndsEq.numRows - 1, 1, 1);
            }
        }

        double[] resData = new double[cableEq.data.length + deadEndsEq.data.length];
        System.arraycopy(cableEq.data, 0, resData, 0, cableEq.data.length);
        System.arraycopy(deadEndsEq.data, 0, resData, cableEq.data.length ,deadEndsEq.data.length);

        return resData;
    }
}
