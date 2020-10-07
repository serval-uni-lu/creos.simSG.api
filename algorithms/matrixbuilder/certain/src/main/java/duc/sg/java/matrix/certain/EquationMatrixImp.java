package duc.sg.java.matrix.certain;


import duc.sg.java.matrix.EquationMatrix;
import duc.sg.java.model.Fuse;

import java.util.Map;

public class EquationMatrixImp implements EquationMatrix {

    protected double[] data;
    protected Map<Fuse, Integer> mapFuseColumnIdx;
    protected Fuse[] columnIdx;
    protected double[] eqResults;

    EquationMatrixImp(double[] data, Map<Fuse, Integer> mapFuseColumnIdx, double[] eqResults) {
        this.data = data;
        this.eqResults = eqResults;
        this.mapFuseColumnIdx = mapFuseColumnIdx;
        columnIdx = new Fuse[mapFuseColumnIdx.size()];
        for(Map.Entry<Fuse, Integer> keyValue: mapFuseColumnIdx.entrySet()) {
            columnIdx[keyValue.getValue()] = keyValue.getKey();
        }

    }

    protected EquationMatrixImp(EquationMatrixImp fuseStatesMatrix) {
        this.data = fuseStatesMatrix.data;
        this.columnIdx = fuseStatesMatrix.columnIdx;
        this.eqResults = fuseStatesMatrix.eqResults;
        this.mapFuseColumnIdx = fuseStatesMatrix.mapFuseColumnIdx;
    }

    @Override
    public double[] getValues() {
        var copy = new double[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    @Override
    public int getNbColumns() {
       return mapFuseColumnIdx.size();
    }

    @Override
    public Integer getColumnIdx(Fuse fuse) {
        return mapFuseColumnIdx.get(fuse);
    }

    @Override
    public Fuse getColumn(int idx) {
        return columnIdx[idx];
    }

    @Override
    public double[] getEqResults(){
        return eqResults;
    }
}
