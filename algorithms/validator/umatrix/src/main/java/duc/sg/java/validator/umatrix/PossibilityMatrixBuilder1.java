package duc.sg.java.validator.umatrix;

import duc.sg.java.circle.all.Circle;
import duc.sg.java.circle.all.CircleUtils;
import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.BaseTransform;

import java.util.*;

public class PossibilityMatrixBuilder1 {
    private PossibilityMatrixBuilder1() {}



    private static Map<Fuse, Integer> collectFuses(Substation substation) {
        final var idxColumns = new HashMap<Fuse, Integer>();

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
                        Entity oppOwner = oppFuse.getOwner();
                        if(!oppOwner.isAlwaysDeadEnd()) {
                            idxColumns.put(oppFuse, ++nextIdxFuse);
                            if(!entityVisited.contains(oppFuse.getOwner())) {
                                waitingList.add(oppFuse.getOwner());
                            }
                        }

                    }
                }
            }
        }

        return idxColumns;
    }

    private static State[] buildMatrix(Map<Fuse, Integer> idxColumns) {
        int nbPossibilities = (int) Math.pow(2, idxColumns.size());
        State[] matrix = new State[nbPossibilities * idxColumns.size()];

        for (int i = 0; i < nbPossibilities; i++) {
            boolean[] state = BaseTransform.toBinary(i, idxColumns.size());

            for (int j = 0; j < state.length; j++) {
                matrix[i * idxColumns.size() + j] = state[j]? State.CLOSED : State.OPEN;
            }
        }

        return matrix;
    }

    private static void copyLine(List<State> dest, State[] source, int idxLine, int nbColumns) {
        for (int iCol = 0; iCol < nbColumns; iCol++) {
            dest.add(source[idxLine * nbColumns + iCol]);
        }
    }

    private static State[] applyRules(Substation substation, State[] initState, Map<Fuse, Integer> idxColumns) {
        var res = new ArrayList<State>();
        int nbLines = (int) Math.pow(2, idxColumns.size());

        var idxCycles = new HashMap<Fuse, Fuse[]>();

        for (int iLine = 0; iLine < nbLines; iLine++) {
            boolean rules1_2Valid = true;
            boolean rule3Valid = true;
            boolean rule4Valid = true;
            for(var entry: idxColumns.entrySet()) {
                Fuse currentFuse = entry.getKey();

                // Rule 1 & 2
                {

                    Fuse oppFuse = currentFuse.getOpposite();
                    Integer iColOpp = idxColumns.get(oppFuse);

                    State currFuseState = initState[iLine * idxColumns.size() + entry.getValue()];

                    if (iColOpp != null) {
                        State oppFuseState = initState[iLine * idxColumns.size() + iColOpp];
                        // Rule 1: In each cable, both fuses cannot be open at the same time
                        if (currFuseState == State.OPEN && oppFuseState == State.OPEN) {
                            rules1_2Valid = false;
                            break;
                        }
                    }
                    // Rule 2: Opposite fuse of one that belongs to a dead end should be closed
                    else if (currFuseState == State.OPEN) {
                        rules1_2Valid = false;
                        break;
                    }
                }

                // Rule 3
                {
                    if(currentFuse.getOwner() instanceof Substation) {
                        List<Fuse> subsFuses = currentFuse.getOwner().getFuses();
                        boolean oneClosed = false;
                        for (Fuse f: subsFuses) {
                            State fState = initState[iLine * idxColumns.size() + idxColumns.get(f)];
                            oneClosed = oneClosed || fState == State.CLOSED;
                        }
                        if(!oneClosed) {
                            rule3Valid = false;
                            break;
                        }
                    }
                }

                //Rule 4
                {
                    Fuse[] cycle;
                    if(!idxCycles.containsKey(currentFuse)) {
                        Circle circle = CircleUtils.circleFrom(substation, currentFuse).get();
                        cycle = circle.getFuses();

                        for (var fuseC: circle.getFuses()) {
                            idxCycles.put(fuseC, circle.getFuses());
                        }
                    } else {
                        cycle = idxCycles.get(currentFuse);
                    }

                    Entity entityWithOpenFuse = null;
                    int nbFuseOpen = 0;
                    for (var fuseCycle: cycle) {
                        State fuseCycleState = initState[iLine * idxColumns.size() + idxColumns.get(fuseCycle)];
                        if(fuseCycleState == State.OPEN) {
                            nbFuseOpen++;
                            if(nbFuseOpen == 1) {
                                Entity currOwner = fuseCycle.getOwner();
                                if(currOwner.mightBeDeadEnd()) {
                                    entityWithOpenFuse = currOwner;
                                }
                            } else  if(nbFuseOpen == 2) {
                                Entity currOwner = fuseCycle.getOwner();
                                if(!currOwner.equals(entityWithOpenFuse)) {
                                    rule4Valid = false;
                                    break;
                                }
                            } else {
                                rule4Valid = false;
                                break;
                            }

                        }
                    }

                    if(!rule4Valid) {
                        break;
                    }

                }

            }

            if(rules1_2Valid && rule3Valid && rule4Valid) {
                copyLine(res, initState, iLine, idxColumns.size());
            }
        }



        return res.toArray(new State[0]);
    }


    public static PossibilityMatrix build(Substation substation) {
        Map<Fuse, Integer> idxColumns = collectFuses(substation);
        State[] completeMatrix = buildMatrix(idxColumns);
        State[] finalMatrix = applyRules(substation, completeMatrix, idxColumns);
        return new PossibilityMatrix(finalMatrix, idxColumns);
    }

}
