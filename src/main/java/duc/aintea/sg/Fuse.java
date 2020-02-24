package duc.aintea.sg;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Fuse {
    private String name;
    private Cable cable;
    private Entity owner;
    private State status;

    private double load;
    private Map<Double, Double> uncertainLoad;

    public Fuse(String name) {
        this.name = name;
        status = new State(true, 1.);
        uncertainLoad = new HashMap<>(1);
        uncertainLoad.put(0., 1.);
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
        this.status.close();
    }

    public void openFuse() {
        this.status.open();
    }

    public boolean isClosed() {
        return status.isClosed();
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

    public Map<Double, Double> getUncertainLoad() {
        return uncertainLoad;
    }

    public void setLoad(Map<Double, Double> loadConfPair) {
        this.uncertainLoad = loadConfPair;
    }

    public State getStatus() {
        return status;
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
        return "Fuse(" +
                "name=" + name +
                ", load=" + load +
                ", state=" + status +
                ")";
    }


}


class MapperFuseName implements Function<Fuse, String> {
    @Override
    public String apply(Fuse fuse) {
        return fuse.getName();
    }
}

