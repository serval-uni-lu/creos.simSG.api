package creos.simsg.api.model;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Entity {
    private final List<Fuse> fuses;
    private final String name;

    public Entity(String name) {
        this.name = name;
        fuses = new ArrayList<>();
    }

    public void addFuses(Fuse... fs) {
        for (Fuse f : fs) {
            fuses.add(f);
            f.setOwner(this);
        }
    }

    public String getName() {
        return name;
    }

    public List<Fuse> getFuses() {
        return Collections.unmodifiableList(fuses);
    }

    public List<Fuse> getClosedFuses() {
        return fuses.stream()
                .filter(Fuse::isClosed)
                .collect(Collectors.toList());
    }

    /**
     * A dead-end entity is an entity with at most one active connection to another entity
     *
     * @return true if it is a dead-end, false otherwise
     */
    public boolean isDeadEnd() {
        return getClosedFuses().size() <= 1;
    }

    /**
     * An entity can be a dead-end if in one valid configuration it is a dead-end. For that, the entity should be
     * connected to exactly another one entity, possibly through more than one cable
     *
     * @return true if the entity can be a dead-end, false otherwise
     */
    public boolean mightBeDeadEnd() {
        // TODO check if it's a real-optimisation - fuses.size() remains low (I would say less than 10), so the stream
        //  API might be sufficiently efficient
        if(fuses.size() == 1) {
            return true;
        }

        return fuses.stream()
                .map(Fuse::getOpposite)
                .map(Fuse::getOwner)
                .distinct()
                .count() == 1;
    }

    /**
     * An entity is always a dead-end is it is connected to exactly another one entity
     * (through active connection or not).
     *
     */
    public boolean isAlwaysDeadEnd() {
        return fuses.size() == 1;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" +
                "name=" + name +
                ", fuses=" + Arrays.toString(fuses.stream().map(Fuse::getName).toArray()) +
                ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entity)) {
            return false;
        }

        var casted = (Entity) obj;
        return this.name.equals(casted.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
