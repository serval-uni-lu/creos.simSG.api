package duc.sg.java.matrix.certain;


import duc.sg.java.matrix.FuseStateMatrix;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;

import java.util.Map;

public class CertainFuseStateMatrix implements FuseStateMatrix {

    protected double[] data;
    protected int nbColumns;
    protected Map<Fuse, Integer> indexFuses;
    protected Fuse[] reverse;
    protected Cable[] mapRowCable;

    CertainFuseStateMatrix(double[] data, int nbColumns, Map<Fuse, Integer> indexFuses, Cable[] mapRowCable) {
        this.data = data;
        this.nbColumns = nbColumns;
        this.mapRowCable = mapRowCable;
        this.indexFuses = indexFuses;
        reverse = new Fuse[indexFuses.size()];
        for(Map.Entry<Fuse, Integer> keyValue: indexFuses.entrySet()) {
            reverse[keyValue.getValue()] = keyValue.getKey();
        }

    }

    protected CertainFuseStateMatrix(CertainFuseStateMatrix fuseStatesMatrix) {
        this.data = fuseStatesMatrix.data;
        this.nbColumns = fuseStatesMatrix.nbColumns;
        this.reverse = fuseStatesMatrix.reverse;
        this.mapRowCable = fuseStatesMatrix.mapRowCable;
    }

    @Override
    public double[] getData() {
        var copy = new double[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    @Override
    public int getNbColumns() {
       return nbColumns;
    }

    @Override
    public Integer getFuseIdx(Fuse fuse) {
        return indexFuses.get(fuse);
    }

    @Override
    public Fuse getFuse(int idx) {
        return reverse[idx];
    }

    @Override
    public Cable[] getCables(){
        return mapRowCable;
    }
}
