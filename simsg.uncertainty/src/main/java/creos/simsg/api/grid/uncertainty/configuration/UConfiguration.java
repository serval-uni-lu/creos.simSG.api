package creos.simsg.api.grid.uncertainty.configuration;

import creos.simsg.api.model.Configuration;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.uncertainty.Confidence;
import creos.simgsg.api.utils.Pair;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class UConfiguration implements Iterable<Pair<Fuse, State>> {
    private final Configuration configuration;
    private final Confidence confidence;

    public UConfiguration(Fuse[] fuses, State[] states, double confidence) {
        this.confidence = new Confidence(confidence);
        this.configuration = new Configuration(fuses, states);

    }

    public double getConfidence() {
        return confidence.getProbability();
    }

    public Iterator<Pair<Fuse, State>> getFuseStates() {
        return this.configuration.getConfiguration()
                .entrySet()
                .stream()
                .map((Map.Entry<Fuse, State> entry) -> new Pair<Fuse,State>(entry.getKey(), entry.getValue()))
                .iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(configuration, ((UConfiguration)o).configuration);
    }


    @Override
    public Iterator<Pair<Fuse, State>> iterator() {
        return this.configuration.getConfiguration()
                .entrySet()
                .stream()
                .map((Map.Entry<Fuse, State> entry) -> new Pair<Fuse,State>(entry.getKey(), entry.getValue()))
                .iterator();
    }
}
