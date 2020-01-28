package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.ejml.data.DenseMatrix64F;

import java.util.HashMap;

public class MatrixBuilder {

    public double[] build(Substation substation) {
//        final var nbFuses = substation.getFuses().size();
//        final var cableEq = new DenseMatrix64F(0);
//        final var deadEndsEq = new DenseMatrix64F(0);

//        for (int i = 0; i < substation.getFuses().size(); i++) {
//            var fuse = substation.getFuses().get(i);
//
//            if(fuse.isClosed()) {
//                final int ii = i;
////                fuse.getOpposite().ifPresent((Entity e) -> {
////                    cableEq.reshape(cableEq.numRows + 1, nbFuses*2, true);
////                    cableEq.add(cableEq.numRows - 1, ii, 1);
////                    cableEq.add(cableEq.numRows - 1, ii + 1, 1);
////                });
////
////                fuse.getOppDeadEnds().ifPresent((Entity e) -> {
////                    cableEq.reshape(cableEq.numRows + 1, nbFuses*2, true);
////                    cableEq.add(cableEq.numRows - 1, ii, 1);
////                    cableEq.add(cableEq.numRows - 1, ii + 1, 1);
////
////                    deadEndsEq.reshape(deadEndsEq.numRows + 1, nbFuses*2, true);
////                    deadEndsEq.add(deadEndsEq.numRows - 1, ii + 1, 1);
////                });
//            } else {
//                cableEq.reshape(cableEq.numRows + 1, nbFuses*2, true);
//            }
//        }
//        double[] resData = new double[cableEq.data.length + deadEndsEq.data.length];
//        System.arraycopy(cableEq.data, 0, resData, 0, cableEq.data.length);
//        System.arraycopy(deadEndsEq.data, 0, resData, cableEq.data.length ,deadEndsEq.data.length);
//
//        return resData;

        final var idxFuses = new HashMap<String, Integer>();
        final var fuses = substation.getFuses();
        var idxLast = new int[]{0};

        final var cableEq = new DenseMatrix64F(0);

        for (int i = 0; i < fuses.size(); i++) {
            var fuse = fuses.get(i);
            var oppFuse = fuse.getOpposite();
            var idxFuse = getOrCreateIdx(fuse, idxFuses, idxLast);
            var idxOpp = getOrCreateIdx(oppFuse, idxFuses, idxLast);
            cableEq.reshape(cableEq.numRows + 1, cableEq.numCols + 2, true);
            cableEq.add(cableEq.numRows - 1, idxFuse, 1);
            cableEq.add(cableEq.numRows - 1, idxOpp, 1);
        }

        return null;
    }

    public int getOrCreateIdx(Fuse fuse, HashMap<String, Integer> map, int[] last) {
        Integer idx = map.get(fuse.getName());
        if(idx == null) {
            last[0] = last[0] + 1;
            idx = last[0];
            map.put(fuse.getName(), idx);
        }
        return idx;
    }
}
