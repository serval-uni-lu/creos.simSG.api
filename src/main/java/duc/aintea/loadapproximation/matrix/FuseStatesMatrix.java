package duc.aintea.loadapproximation.matrix;

import duc.aintea.sg.Fuse;

import java.util.Collections;
import java.util.Map;

public class FuseStatesMatrix {

    private double[] data;
    private int nbColumns;
    private Map<Fuse, Integer> indexFuses;
    private Fuse[] reverse;


    public FuseStatesMatrix(double[] data, int nbColumns, Map<Fuse, Integer> indexFuses) {
        this.data = data;
        this.nbColumns = nbColumns;
        this.indexFuses = indexFuses;

        reverse = new Fuse[indexFuses.size()];
        for(Fuse key: indexFuses.keySet()) {
            reverse[indexFuses.get(key)] = key;
        }

    }

    public double[] getData() {
        var copy = new double[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public int getNbColumns() {
       return nbColumns;
    }

    public Map<Fuse, Integer> getIndexFuses() {
        return Collections.unmodifiableMap(indexFuses);
    }

    public Fuse getFuse(int idx) {
        return reverse[idx];
    }
}
