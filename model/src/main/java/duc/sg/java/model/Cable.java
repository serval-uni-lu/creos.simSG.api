package duc.sg.java.model;

import duc.sg.java.uncertainty.MultDblPoss2;
import duc.sg.java.uncertainty.PossibilityDouble;

import java.util.*;

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

    public MultDblPoss2 getUncertainLoad() {
//        if(fuses[0] != null && fuses[1] != null) {
//            return UMath.max(fuses[0].getUncertainLoad(), fuses[1].getUncertainLoad());
//        } else if(fuses[0] != null) {
//            return fuses[0].getUncertainLoad();
//        }
//
//        return fuses[1].getUncertainLoad();

        if(fuses[0] == null) {
            return fuses[1].getUncertainLoad();
        }

        if(fuses[1] == null) {
            return fuses[0].getUncertainLoad();
        }

        MultDblPoss2 res = new MultDblPoss2();
//        for (PossibilityDouble f1Poss: fuses[0].getUncertainLoad()) {
//            for (PossibilityDouble f2Poss: fuses[1].getUncertainLoad()) {
//                res.addPossibility(new PossibilityDouble(
//                        Math.max(f1Poss.getValue(), f2Poss.getValue()),
//                        f1Poss.getConfidence().getProbability()
//                ));
//            }
//        }
        Iterator<PossibilityDouble> itF1Poss = fuses[0].getUncertainLoad().iterator();
        Iterator<PossibilityDouble> itF2Poss = fuses[1].getUncertainLoad().iterator();

        while (itF1Poss.hasNext()) {
            PossibilityDouble f1Poss = itF1Poss.next();
            PossibilityDouble f2Poss = itF2Poss.next();

            res.addPossibility(new PossibilityDouble(
                        Math.max(f1Poss.getValue(), f2Poss.getValue()),
                        f1Poss.getConfidence().getProbability()
            ));

        }

        return res;
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
                 ", load=" + getUncertainLoad() +
                ')';
    }
}
