package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Entity;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.utils.Matrix;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MatrixBuilder {

    public double[] build(Substation substation) {
        final var idxFuses = new HashMap<String, Integer>();
        var idxLast = new int[]{-1};

        final var cableEq = new Matrix();
        final var cabinetEq = new Matrix();

        var waitingList = new ArrayDeque<Entity>();
        var entityVisited = new ArrayDeque<Entity>();

        waitingList.add(substation);

        while(!waitingList.isEmpty()) {
            var currEntity = waitingList.remove();
            entityVisited.add(currEntity);

            final var fuses = currEntity.getFuses().stream().filter(Fuse::isClosed).collect(Collectors.toList());

            if(fuses.size() > 1) {
                cabinetEq.addLine();
            }

            for (Fuse fuse : fuses) {
                if(!idxFuses.containsKey(fuse.getName())) {
                    var oppFuse = fuse.getOpposite();
                    cableEq.addLine();
                    cableEq.addColumn();
                    cabinetEq.addColumn();
//                    if(fuse.isClosed()) {
                        var idxFuse = getOrCreateIdx(fuse, idxFuses, idxLast);
                        cableEq.set(cableEq.getNumRows() - 1, idxFuse, 1);

                        if(!oppFuse.getOwner().isDeadEnd()) {
                            var idxOpp = getOrCreateIdx(oppFuse, idxFuses, idxLast);
                            cableEq.addColumn();
                            cabinetEq.addColumn();

                            cableEq.set(cableEq.getNumRows() - 1, idxOpp, 1);
                            if(!entityVisited.contains(oppFuse.getOwner())) {
                                waitingList.add(oppFuse.getOwner());
                            }
                        }
//                    }
                }

                if(fuses.size() > 1) {
                    var idxFuse = getOrCreateIdx(fuse, idxFuses, idxLast);
                    cabinetEq.set(cabinetEq.getNumRows() - 1, idxFuse, 1);
                }
            }


        }

        double[] resData = new double[cableEq.getData().length + cabinetEq.getData().length];
        System.arraycopy(cableEq.getData(), 0, resData, 0, cableEq.getData().length);
        System.arraycopy(cabinetEq.getData(), 0, resData, cableEq.getData().length, cabinetEq.getData().length);
        return (resData.length == 0)? new double[]{0} : resData;
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
