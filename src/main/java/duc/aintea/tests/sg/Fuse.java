package duc.aintea.tests.sg;

import java.util.function.Function;

public class Fuse {
    public String name;
    private Cable cable;
    public Entity owner;

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
}


class MapperFuseName implements Function<Fuse, String> {
    @Override
    public String apply(Fuse fuse) {
        return fuse.name;
    }
}
