package duc.aintea.tests.sg;

import java.util.Optional;
import java.util.function.Function;

public class Fuse {
    private String name;
    private Cable cable;
    private Entity owner;
    private State status;

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

    public Optional<Entity> getOpposite() {
        if(isClosed()) {
            Fuse first = cable.getFirstFuse();
            if (first == this) {
                Fuse second = cable.getSecondFuse();
                return (second.isClosed())? Optional.of(second.owner) : Optional.empty();
            }
            return (first.isClosed())? Optional.of(first.owner) : Optional.empty();
        }
        return Optional.empty();
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
}


class MapperFuseName implements Function<Fuse, String> {
    @Override
    public String apply(Fuse fuse) {
        return fuse.getName();
    }
}

