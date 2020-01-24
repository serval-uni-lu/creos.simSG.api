package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Grid;
import org.ejml.data.DenseMatrix64F;

import java.util.List;

public class MatrixBuilder {

    public double[] build(Grid grid) {
        var cableEq = new DenseMatrix64F(0);
        var deadEndsEq = new DenseMatrix64F(0);


        var substation = grid.getSubstations().get(0);

        List<Fuse> fuses = substation.getFuses();
        for (int i = 0; i < fuses.size(); i++) {
            var f = fuses.get(i);
            var opposite = f.getOpposite();
            if (f.getOpposite() != null) {
                cableEq.reshape(cableEq.numRows + 1, cableEq.numRows + 2, true);
                deadEndsEq.reshape(deadEndsEq.numRows, cableEq.numCols, true);
                cableEq.add(i, 0, 1);
                cableEq.add(i, 1,1);
            }

            if(opposite.isDeadEnd()) {
               deadEndsEq.reshape(deadEndsEq.numRows + 1, deadEndsEq.numCols, true);
               deadEndsEq.set(deadEndsEq.numRows - 1, i+1, 1);
            }
        }

        double[] resData = new double[cableEq.data.length + deadEndsEq.data.length];
        System.arraycopy(cableEq.data, 0, resData, 0, cableEq.data.length);
        System.arraycopy(deadEndsEq.data, 0, resData, cableEq.data.length ,deadEndsEq.data.length);



        return resData;
    }
}
