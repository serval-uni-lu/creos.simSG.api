package duc.aintea.tests.sg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    public List<Entity> getReachableNeighbors() {
        final var result = new ArrayList<Entity>(fuses.size());
        fuses.stream().filter(Fuse::isClosed).forEach(f -> {
            Fuse opposite = f.getOpposite();
            if(opposite.isClosed()) {
                result.add(opposite.getOwner());
            }
        });
        return result;
    }


    public boolean isDeadEnd() {
        List<Entity> reachNeigh = getReachableNeighbors();
        if(reachNeigh.size() == 1) {
            return true;
        }

        var names = new HashSet<>(fuses.size());
        for (int i = 0; i < reachNeigh.size(); i++) {
            Entity entity = reachNeigh.get(i);
            if(names.add(entity.getName()) && i>0) {
                return false;
            }
        }

        return true;
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
        if(!(obj instanceof Entity)) {
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
