package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;

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

}
