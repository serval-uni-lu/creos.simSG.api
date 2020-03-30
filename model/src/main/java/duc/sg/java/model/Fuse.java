package duc.sg.java.model;

import duc.sg.java.uncertainty.Confidence;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;

public class Fuse {
    private String name;
    private Cable cable;
    private Entity owner;
    private Status status;
    private MultDblePossibilities uload;


    public Fuse(String name) {
        this(name, State.CLOSED);
    }

    public Fuse(String name, State state) {
        this.name = name;
        status = new Status(state, 1.);
        uload = new MultDblePossibilities();
        uload.addOrReplace(new PossibilityDouble(0., Confidence.MAX_PROBABILITY));
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

    public void setConfIsClosed(double confidence) {
        status.setConfIsClosed(confidence);
    }

    public void setConfIsOpen(double confidence) {
        status.setConfIsOpen(confidence);
    }

    public Fuse getOpposite() {
        var f = cable.getFirstFuse();
        if(this == f) return cable.getSecondFuse();
        return f;
    }

    public MultDblePossibilities getUncertainLoad() {
        return uload;
    }

    public void setLoad(MultDblePossibilities uload) {
        this.uload = uload;
    }

    public Status getStatus() {
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
                "uload=" + uload +
                ", state=" + status +
                ")";
    }


}


