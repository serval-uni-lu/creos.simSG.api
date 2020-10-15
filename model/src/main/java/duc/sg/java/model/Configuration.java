package duc.sg.java.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A topology configuration is a set of possible states for fuses.
 * The model should store the effective state of the grid. A configuration should be use to explore alternative states.
 */
public class Configuration {
    private final Map<Fuse, State> configuration;

    public Configuration(Map<Fuse, State> configuration) {
        this.configuration = configuration;
    }

    public Configuration(Fuse[] fuses, State[] states) {
        this.configuration = new HashMap<>();
        for (int i = 0; i < fuses.length; i++) {
            this.configuration.put(fuses[i], states[i]);
        }
    }

    public Map<Fuse, State> getConfiguration() {
        return configuration;
    }

    public boolean isClosed(Fuse fuse) {
        return configuration.get(fuse) == State.CLOSED;
    }

    public List<Fuse> getClosedFuses(Entity entity) {
        return entity.getFuses()
                .stream()
                .filter((Fuse fuse) -> configuration.get(fuse) == State.CLOSED)
                .collect(Collectors.toList());
    }

    public void set(Fuse fuse, State newState) {
        configuration.put(fuse, newState);
    }

    public boolean isDeadEnd(Entity entity) {
        return getClosedFuses(entity).size() <= 1;
    }

    public State getState(Fuse fuse) {
        return this.configuration.get(fuse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configuration);
    }
}
