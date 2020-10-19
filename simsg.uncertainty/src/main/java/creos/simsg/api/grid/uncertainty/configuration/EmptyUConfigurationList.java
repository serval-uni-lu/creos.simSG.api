package creos.simsg.api.grid.uncertainty.configuration;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;

import java.util.ArrayList;

public class EmptyUConfigurationList extends UConfigurationList {
    public EmptyUConfigurationList() {
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
