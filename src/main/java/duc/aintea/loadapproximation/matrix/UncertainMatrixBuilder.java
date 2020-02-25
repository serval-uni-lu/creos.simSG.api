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
        var waiting = new ArrayDeque<>(substation.getClosedAndUncertainFuses());

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            if(!current.getOwner().isDeadEnd() && current.getStatus().isUncertain()) {
                uFuses.add(current);
            }

            var opp = current.getOpposite();
            if(opp.isClosed() && !opp.getOwner().isDeadEnd() && opp.getStatus().isUncertain()) {
                uFuses.add(opp);
            }

            visited.add(current);
            visited.add(opp);

            var ownerOpp = opp.getOwner();
            for(var f: ownerOpp.getClosedAndUncertainFuses()) {
                if(!visited.contains(f)) {
                    waiting.add(f);
                }
            }
        }


        return uFuses;
    }

    public static UncertainFuseStatesMatrix[] build(Substation substation) {
        List<Fuse> uFuses = getUncertainFuses(substation);
        var nbPossibilities = (int) Math.pow(2, uFuses.size());

        var res = new UncertainFuseStatesMatrix[nbPossibilities];
        for (int idxCase = 0; idxCase < nbPossibilities; idxCase++) {
            boolean[] fuseStates = BaseTransform.toBinary(idxCase, uFuses.size());
            double confidence = 1;

            for (int idxFuse = 0; idxFuse < uFuses.size(); idxFuse++) {
                if(fuseStates[idxFuse]){
                    uFuses.get(idxFuse).closeFuse();
                    confidence *= uFuses.get(idxFuse).getStatus().getConfClosedAsProb();
                } else {
                    uFuses.get(idxFuse).openFuse();
                    confidence *= uFuses.get(idxFuse).getStatus().getConfOpenAsProb();
                }
            }
            res[idxCase] = new UncertainFuseStatesMatrix(MatrixBuilder.build(substation), confidence);
        }

        return res;
    }
}
