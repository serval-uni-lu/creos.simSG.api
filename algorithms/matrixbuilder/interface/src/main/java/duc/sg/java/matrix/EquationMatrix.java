package duc.sg.java.matrix;

import duc.sg.java.model.Fuse;

public interface EquationMatrix {
    double[] getValues();
    int getNbColumns();
    Integer getColumnIdx(Fuse fuse);
    Fuse getColumn(int idx);
    double[] getEqResults();
}
