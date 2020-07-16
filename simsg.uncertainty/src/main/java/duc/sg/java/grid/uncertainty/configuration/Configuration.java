package duc.sg.java.grid.uncertainty.configuration;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.utils.Pair;

import java.util.Iterator;

public class Configuration {
    private double confidence;
    private Fuse[] fuses;
    private State[] states;

    public Configuration(Fuse[] fuses, State[] states, double confidence) {
        this.confidence = confidence;
        this.fuses = fuses;
        this.states = states;
    }

    public double getConfidence() {
        return confidence;
    }

    public Iterator<Pair<Fuse, State>> getFuseStates() {
        return new Iterator<>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < fuses.length;
            }

            @Override
            public Pair<Fuse, State> next() {
                int tmp = idx;
                idx++;
                return new Pair<>(fuses[tmp], states[tmp]);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

//        if(confidence != that.confidence) {
//            return false;
//        }

        Iterator<Pair<Fuse, State>> thisFuseStateIt = this.getFuseStates();
        while (thisFuseStateIt.hasNext()) {
            Pair<Fuse, State> it = thisFuseStateIt.next();

            Iterator<Pair<Fuse, State>> thatIt = that.getFuseStates();
            boolean exists = false;
            while (thatIt.hasNext()) {
                Pair<Fuse, State> oIt = thatIt.next();
                if(oIt.getFirst().equals(it.getFirst()) && oIt.getSecond().equals(it.getSecond())) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                return false;
            }
        }

        return true;
    }

}
