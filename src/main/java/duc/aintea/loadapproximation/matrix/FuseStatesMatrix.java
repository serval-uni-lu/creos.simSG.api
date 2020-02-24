package duc.aintea.loadapproximation.matrix;

import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;

import java.util.Map;

public class FuseStatesMatrix {

    protected double[] data;
    protected int nbColumns;
    protected Fuse[] reverse;
    protected Cable[] mapRowCable;


    public FuseStatesMatrix(double[] data, int nbColumns, Map<Fuse, Integer> indexFuses, Cable[] mapRowCable) {
        this.data = data;
        this.nbColumns = nbColumns;
        this.mapRowCable = mapRowCable;

        reverse = new Fuse[indexFuses.size()];
        for(Map.Entry<Fuse, Integer> keyValue: indexFuses.entrySet()) {
            reverse[keyValue.getValue()] = keyValue.getKey();
        }

    }

    protected FuseStatesMatrix(double[] data, int nbColumns, Fuse[] reverse, Cable[] mapRowCabl) {
        this.data = data;
        this.nbColumns = nbColumns;
        this.reverse = reverse;
        this.mapRowCable = mapRowCabl;
    }

    public double[] getData() {
        var copy = new double[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public int getNbColumns() {
       return nbColumns;
    }

    public Fuse getFuse(int idx) {
        return reverse[idx];
    }

    public Cable[] getCables(){
        return mapRowCable;
    }
}
