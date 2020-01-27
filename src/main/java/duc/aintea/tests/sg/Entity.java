package duc.aintea.tests.sg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Entity {
    private List<Fuse> fuses;
    private String name;

    public Entity(String name) {
        this.name = name;
        fuses = new ArrayList<>();
    }

    public void addFuses(Fuse... fs) {
       for (Fuse f: fs) {
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

    private List<Entity> prv_getReachableEntites(boolean deadends) {
        var result = new ArrayList<Entity>(fuses.size());

        for (var f: fuses) {
            Optional<Entity> opposite = f.prv_getOpposite(deadends);
            opposite.ifPresent(result::add);
        }

        return result;
    }

    public List<Entity> getReachableEntities() {
        return prv_getReachableEntites(false);
    }

    public List<Entity> getReachableDeadEnds() {
        return prv_getReachableEntites(true);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" +
                "name=" + name +
                ", fuses=" + Arrays.toString(fuses.stream().map(new MapperFuseName()).toArray()) +
                ")";
    }

    public boolean isDeadEnd() {
        return false;
    }
}
