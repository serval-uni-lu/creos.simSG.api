package duc.aintea.tests.sg;

import java.util.function.Function;

public class Fuse {
    private String name;
    private Cable cable;
    private Entity owner;
    private State status;

    private double load;

    public Fuse(String name) {
        this.name = name;
        status = State.CLOSED;
    }


    public Cable getCable() {
        return cable;
    }

    void setCable(Cable cable) {
        this.cable = cable;
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

    public void closeFuse() {
        this.status = State.CLOSED;
    }

    public void openFuse() {
        this.status = State.OPEN;
    }

    public boolean isClosed() {
        return status == State.CLOSED;
    }

    public Fuse getOpposite() {
        var f = cable.getFirstFuse();
        if(this == f) return cable.getSecondFuse();
        return f;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Fuse)) {
            return false;
        }

        var casted = (Fuse) obj;
        return this.name.equals(casted.name);
    }

    @Override
    public String toString() {
        return "Fuse(" + name + ")";
    }
}


class MapperFuseName implements Function<Fuse, String> {
    @Override
    public String apply(Fuse fuse) {
        return fuse.getName();
    }
}

