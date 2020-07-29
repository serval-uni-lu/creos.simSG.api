package duc.sg.java.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Configuration {
    private final Map<Fuse, State> configuration;


    public Configuration(Map<Fuse, State> configuration) {
        this.configuration = configuration;
    }

    public boolean isClosed(Fuse fuse) {
        return configuration.get(fuse) == State.CLOSED;
    }

    public List<Fuse> getClosedFuses(Entity entity) {
        return entity.getFuses().stream()
                .filter((Fuse fuse) -> configuration.get(fuse) == State.CLOSED)
                .collect(Collectors.toList());
    }

    public void set(Fuse fuse, State newState) {
        configuration.put(fuse, newState);
    }

    public boolean isDeadEnd(Entity entity) {
        return getClosedFuses(entity).size() <= 1;
    }


}
