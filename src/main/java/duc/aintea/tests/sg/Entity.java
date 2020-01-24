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


    public List<Entity> getReachableEntities() {
        var result = new ArrayList<Entity>(fuses.size());

        for (var f: fuses) {
            Optional<Entity> opposite = f.getOpposite();
            opposite.ifPresent(result::add);
        }

        return result;
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
