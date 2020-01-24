package duc.aintea.tests.sg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Entity {
    List<Fuse> fuses;
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
