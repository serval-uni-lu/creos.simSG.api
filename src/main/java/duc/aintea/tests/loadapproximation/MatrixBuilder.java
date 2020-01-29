package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.ejml.data.DenseMatrix64F;

import java.util.HashMap;

public class MatrixBuilder {

    public double[] build(Substation substation) {
        final var idxFuses = new HashMap<String, Integer>();
        final var fuses = substation.getFuses();
        var idxLast = new int[]{-1};

        final var cableEq = new DenseMatrix64F(0);

        for (Fuse fuse : fuses) {
            var oppFuse = fuse.getOpposite();
            var idxFuse = getOrCreateIdx(fuse, idxFuses, idxLast);
            var idxOpp = getOrCreateIdx(oppFuse, idxFuses, idxLast);
            cableEq.reshape(cableEq.numRows + 1, cableEq.numCols + 2, true);
            if(fuse.isClosed()) {
                cableEq.add(cableEq.numRows - 1, idxFuse, 1);
                if(!oppFuse.getOwner().isDeadEnd()) {
                    cableEq.add(cableEq.numRows - 1, idxOpp, 1);
                }
            }

        }

        double[] resData = new double[cableEq.data.length/* + deadEndsEq.data.length*/];
        System.arraycopy(cableEq.data, 0, resData, 0, cableEq.data.length);

        return resData;
    }

    private int getOrCreateIdx(Fuse fuse, HashMap<String, Integer> map, int[] last) {
        Integer idx = map.get(fuse.getName());
        if(idx == null) {
            last[0] = last[0] + 1;
            idx = last[0];
            map.put(fuse.getName(), idx);
        }
        return idx;
    }
}
