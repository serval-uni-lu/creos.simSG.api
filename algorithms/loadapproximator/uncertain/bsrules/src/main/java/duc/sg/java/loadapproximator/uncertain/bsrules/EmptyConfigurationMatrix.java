package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;

import java.util.ArrayList;

public class EmptyConfigurationMatrix extends ConfigurationMatrix {
    public EmptyConfigurationMatrix() {
        this.confidences = new ArrayList<>(1);
        this.confidences.add(1.);

        this.states = new State[0];
        this.columns = new Fuse[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
