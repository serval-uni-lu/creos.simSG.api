package duc.aintea.tests.sg;

import java.util.function.Function;

public class Fuse {
    private String name;
    private Cable cable;
    private Entity owner;

    public Fuse(String name) {
        this.name = name;
    }


    public Cable getCable() {
        return cable;
    }

    void setCable(Cable cable) {
        this.cable = cable;
    }

    public Entity getOpposite() {
        Fuse first = cable.getFirstFuse();
        if(first == this) {
            return cable.getSecondFuse().owner;
        }

        return first.owner;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


class MapperFuseName implements Function<Fuse, String> {
    @Override
    public String apply(Fuse fuse) {
        return fuse.getName();
    }
}
