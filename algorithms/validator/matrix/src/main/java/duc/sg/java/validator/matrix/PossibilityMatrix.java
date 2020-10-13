package duc.sg.java.validator.matrix;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Map;

public class PossibilityMatrix {
    State[] matrix;
    Map<Fuse, Integer> idxColumns;


    PossibilityMatrix( State[] matrix, Map<Fuse, Integer> idxColumns) {
        this.matrix = matrix;
        this.idxColumns = idxColumns;
    }

    private void init(Substation substation) {


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

        res.append(String.format(format, ""));
        for (int i = 0; i < reverseIdx.length; i++) {
            res.append(String.format(format, reverseIdx[i].getName()));
        }
        res.append("\n");
        res.append(String.format(format, "1"));

        for (int i = 0; i < matrix.length; i++) {
            State state = matrix[i];
            res.append(String.format(format, state));

            if( (i+1) % idxColumns.size() == 0) {
                res.append(("\n"));

                if(i != matrix.length-1) {
                    res.append(String.format(format, (i / idxColumns.size() + 2) + ""));
                }
            }
        }


        return res.toString();
    }
}
