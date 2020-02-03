package duc.aintea.tests.sg;

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

   private List<Fuse> getClosedFuses() {
        return fuses.stream().filter(Fuse::isClosed).collect(Collectors.toList());
    }


    public boolean isDeadEnd() {
        var closedFuses = getClosedFuses();
        if(closedFuses.size() == 1) {
            return true;
        }
        
        var entityNames = new HashSet<String>(closedFuses.size());
        for (int i = 0; i < closedFuses.size(); i++) {
            var current = closedFuses.get(i);
            var oppFuse = current.getOpposite();
            var oppOwner = oppFuse.getOwner();

            if(oppFuse.isClosed() || oppOwner.isDeadEnd()) {
                var newElmt = entityNames.add(oppOwner.name);
                if(newElmt && i>0) {
                   return false;
                }
            } else if(!oppFuse.isClosed()) {
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
