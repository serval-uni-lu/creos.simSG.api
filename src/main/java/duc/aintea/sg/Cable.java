package duc.aintea.sg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Cable {
    private Fuse[] fuses;
    private List<Meter> meters;

    public Cable() {
        fuses = new Fuse[2];
        meters = new ArrayList<>();
    }

    private void setFuse(int idx, Fuse f) {
        fuses[idx] = f;
        f.setCable(this);
    }

    private Fuse getFuse(int idx) {
        return fuses[idx];
    }


    public void setFirstFuse(Fuse f) {
        setFuse(0, f);
    }

    public void setSecondFuse(Fuse f) {
        setFuse(1, f);
    }

    public void setFuses(Fuse first, Fuse second) {
        setFuse(0, first);
        setFuse(1, second);
    }

    public Fuse getFirstFuse() {
        return getFuse(0);
    }

    public Fuse getSecondFuse() {
        return getFuse(1);
    }

    public void addMeters(Meter... ms) {
        Collections.addAll(meters, ms);
    }

    public List<Meter> getMeters() {
        return new ArrayList<>(meters);
    }

    public double getConsumption() {
        double consumption = 0;
        for(var m: meters) {
            consumption+= m.getConsumption();
        }
        return consumption;
    }

    public double getLoad() {
        return Math.max(fuses[0].getLoad(), fuses[1].getLoad());
    }

    @Override
    public int hashCode() {
        var s = fuses[0].getName() + "->" + fuses[1].getName();
        return s.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Cable)) {
            return false;
        }

        var casted = (Cable) obj;

        return fuses[0].equals(casted.fuses[0]) && fuses[1].equals(casted.fuses[1]);
    }

    @Override
    public String toString() {
         return "Cable(" +
                "fuses=" + Arrays.toString(Arrays.stream(fuses).map(new MapperFuseName()).toArray()) +
                ", meters=" + Arrays.toString(meters.stream().map(Meter::getName).toArray()) +
                 ", consumption=" + getConsumption() +
                 ", load=" + getLoad() +
                ')';
    }
}
