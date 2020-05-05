package duc.sg.java.matrix.uncertain;

import duc.sg.java.matrix.certain.MatrixBuilder;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.BaseTransform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class UncertainMatrixBuilder {
    private UncertainMatrixBuilder(){}


    private static List<Fuse> getUncertainFuses(Substation substation) {
        var uFuses = new ArrayList<Fuse>();
        var waiting = new Stack<Fuse>();

        var visited = new HashSet<Fuse>();
        var added = new HashSet<Fuse>();

        waiting.add(substation.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            if(!visited.contains(current) && !current.getOwner().isDeadEnd() && current.getStatus().isUncertain()) {
                uFuses.add(current);
            }
            visited.add(current);

            var ownerOpp = current.getOpposite().getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f) && !added.contains(f)) {
                    waiting.add(f);
                    added.add(f);
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
                    confidence *= uFuses.get(idxFuse).getStatus().confIsClosed();
                } else {
                    uFuses.get(idxFuse).openFuse();
                    confidence *= uFuses.get(idxFuse).getStatus().confIsOpen();
                }
            }
            res[idxCase] = new UncertainFuseStatesMatrix(MatrixBuilder.build(substation), confidence);
        }

        return res;
    }
}
