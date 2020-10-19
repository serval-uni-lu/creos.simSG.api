package creos.simsg.api.matrix;

import creos.simsg.api.model.Fuse;

/**
 * Representation of the equation matrix.
 * Load approximation is done by solving an equation system where the variable are the fuse loads and the consumption.
 * This matrix represents this system of equation
 */
public interface EquationMatrix {
    double[] getValues();
    int getNbColumns();
    Integer getColumnIdx(Fuse fuse);
    Fuse getColumn(int idx);
    double[] getEqResults();
}
