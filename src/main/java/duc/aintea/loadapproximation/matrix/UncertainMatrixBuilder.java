package duc.aintea.loadapproximation.matrix;

import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.utils.BaseTransform;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UncertainMatrixBuilder {
    private UncertainMatrixBuilder(){}


    private static List<Fuse> getUncertainFuses(Substation substation) {
        var uFuses = new ArrayList<Fuse>();

        var visited = new HashSet<Fuse>();
        var waiting = new ArrayDeque<>(substation.getClosedFuses());

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            if(!current.getOwner().isDeadEnd() && current.getConfidence() != 100) {
                uFuses.add(current);
            }

            var opp = current.getOpposite();
            if(opp.isClosed() && !opp.getOwner().isDeadEnd() && opp.getConfidence() != 100) {
                uFuses.add(opp);
            }

            visited.add(current);
            visited.add(opp);

            var ownerOpp = opp.getOwner();
            for(var f: ownerOpp.getClosedFuses()) {
                if(!visited.contains(f)) {
                    waiting.add(f);
                }
            }
        }


        return uFuses;
    }

    public static FuseStatesMatrix[] build(Substation substation) {
        List<Fuse> uFuses = getUncertainFuses(substation);
        var nbPossibilities = (int) Math.pow(2, uFuses.size());

        var res = new FuseStatesMatrix[nbPossibilities];
        for (int idxCase = 0; idxCase < nbPossibilities; idxCase++) {
            boolean[] fuseStates = BaseTransform.toBinary(idxCase, uFuses.size());

            for (int idxFuse = 0; idxFuse < uFuses.size(); idxFuse++) {
                if(fuseStates[idxFuse]){
                    uFuses.get(idxFuse).closeFuse();
                } else {
                    uFuses.get(idxFuse).openFuse();
                }
            }
            res[idxCase] = MatrixBuilder.build(substation);
        }

        return res;
    }
}
