package duc.sg.java.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Entity {
    private List<Fuse> fuses;
    private String name;

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
        return new ArrayList<>(fuses);
    }

    public List<Fuse> getClosedFuses() {
        return fuses.stream()
                .filter(Fuse::isClosed)
                .collect(Collectors.toList());
    }

    public boolean isDeadEnd() {
        return getClosedFuses().size() <= 1;
    }

    public boolean mightBeDeadEnd() {
        if(fuses.size() == 1) {
            return true;
        }

        var neighbors = new HashSet<Entity>(fuses.size());
        for(Fuse f: fuses) {
            neighbors.add(f.getOpposite().getOwner());
        }
        return neighbors.size() == 1;
    }

    public boolean isAlwaysDeadEnd() {
        return fuses.size() == 1;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" +
                "name=" + name +
                ", fuses=" + Arrays.toString(fuses.stream().map(new MapperFuseName()).toArray()) +
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
