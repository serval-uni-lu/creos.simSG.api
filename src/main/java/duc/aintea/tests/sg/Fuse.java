package duc.aintea.tests.sg;

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

    //    Optional<Entity> prv_getOpposite(boolean deadends) {
//        if(isClosed()) {
//            var f = cable.getFirstFuse();
//            if(f == this) {
//                f = cable.getSecondFuse();
//            }
//
//            Entity opposite = f.owner;
//            if((deadends && opposite.isDeadEnd()) || (!deadends && f.isClosed() && !opposite.isDeadEnd())) {
//                return Optional.of(opposite);
//            }
//            return Optional.empty();
//        }
//        return Optional.empty();
//    }
//
//
//    public Optional<Entity> getOpposite() {
//        return prv_getOpposite(false);
//    }
//
//    public Optional<Entity> getOppDeadEnds() {
//        return prv_getOpposite(true);
//    }

    public Fuse getOpposite() {
        var f = cable.getFirstFuse();
        if(this == f) return cable.getSecondFuse();
        return f;
    }
}


class MapperFuseName implements Function<Fuse, String> {
    @Override
    public String apply(Fuse fuse) {
        return fuse.getName();
    }
}

