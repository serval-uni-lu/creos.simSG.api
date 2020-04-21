package duc.sg.java.validator.umatrix;

import duc.sg.java.utils.BaseTransform;
import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PossibilityMatrix {
    State[] matrix;
    Map<Fuse, Integer> idxColumns;


    public PossibilityMatrix(Substation substation) {
        idxColumns = new HashMap<>();
        init(substation);
    }

    private void init(Substation substation) {
        int nextIdxFuse = -1;

        var waitingList = new ArrayDeque<Entity>();
        var entityVisited = new HashSet<Entity>();

        waitingList.add(substation);

        while(!waitingList.isEmpty()) {
            var currEntity = waitingList.remove();
            boolean notYetVisited = entityVisited.add(currEntity);

            if(notYetVisited) {
                for(Fuse fuse: currEntity.getFuses()) {
                    if(!idxColumns.containsKey(fuse)) {
                        idxColumns.put(fuse, ++nextIdxFuse);

                        Fuse oppFuse = fuse.getOpposite();
                        idxColumns.put(oppFuse, ++nextIdxFuse);

                        if(!entityVisited.contains(oppFuse.getOwner())) {
                            waitingList.add(oppFuse.getOwner());
                        }
                    }
                }
            }
        }

        int nbPossibilities = (int) Math.pow(2, idxColumns.size());
        matrix = new State[nbPossibilities * idxColumns.size()];

        for (int i = 0; i < nbPossibilities; i++) {
            boolean[] state = BaseTransform.toBinary(i, idxColumns.size());

            for (int j = 0; j < state.length; j++) {
                matrix[i * idxColumns.size() + j] = state[j]? State.CLOSED : State.OPEN;
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        Fuse[] reverseIdx = new Fuse[idxColumns.size()];
        int maxNameLength = 0;
        for(var entry: idxColumns.entrySet()) {
            reverseIdx[entry.getValue()] = entry.getKey();
            maxNameLength = Math.max(maxNameLength, entry.getKey().getName().length());
        }

        String format = "%-" + maxNameLength + "s ";

        for (int i = 0; i < reverseIdx.length; i++) {
            res.append(String.format(format, reverseIdx[i].getName()));
        }
        res.append("\n");

        for (int i = 0; i < matrix.length; i++) {
            State state = matrix[i];
            res.append(String.format(format, state));

            if( (i+1) % idxColumns.size() == 0) {
                res.append(("\n"));
            }
        }


        return res.toString();
    }
}
