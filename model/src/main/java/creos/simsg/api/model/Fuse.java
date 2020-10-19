package creos.simsg.api.model;

import creos.simsg.api.uncertainty.MultiplePossibilities;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Fuse {
    private final String id;
    private final String name;
    private Cable cable;
    private Entity owner;
    private final Status status;

    // TODO remove the following fields (see https://github.com/UL-SnT-Serval/creos.simSG.api/issues/3)
    private MultiplePossibilities uload;
    private Fuse[] getPowerFrom;
    private Fuse[] givePowerTo;


    public Fuse(String name) {
        this(UUID.randomUUID().toString(), name, State.CLOSED);
    }

    public Fuse(String id, String name) {
        this(id, name, State.CLOSED);
    }

    public Fuse(String id, String name, State state) {
        this.name = name;
        this.id = id;
        this.status = new Status(state, 1.);
        this.givePowerTo = new Fuse[0];
    }

    public String getId() {
        return id;
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
        if(this.equals(f)) return cable.getSecondFuse();
        return f;
    }

    public List<Fuse> getNeighbors() {
        var res = getOwner().getFuses()
                .stream()
                .filter((Fuse fuse) -> !fuse.equals(this))
                .collect(Collectors.toList());
        res.add(getOpposite());
        return Collections.unmodifiableList(res);

    }

    public MultiplePossibilities getUncertainLoad() {
        if(uload == null) {
            uload = new MultiplePossibilities();
        }
        return uload;
    }

    public void setLoad(MultiplePossibilities uload) {
        this.uload = uload;
    }

    public void resetULoad() {
        uload = null;
    }

    public Status getStatus() {
        return status;
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Fuse)) {
            return false;
        }

        var casted = (Fuse) obj;
        return this.id.equals(casted.id);
    }

    @Override
    public String toString() {
        return "Fuse(" +
                "name=" + name +
                ", uload=" + uload +
                ", state=" + status +
                ")";
    }

    public Fuse[] getPowerFrom() {
        return getPowerFrom;
    }

    public void setPowerFrom(Fuse[] getPowerFrom) {
        this.getPowerFrom = getPowerFrom;
        for(var f: this.getPowerFrom) {
            f.addPowerDest(this);
        }
    }

    public Fuse[] givePowerTo() {
        return givePowerTo;
    }

    private void addPowerDest(Fuse dest) {
        var newGPT = new Fuse[givePowerTo.length + 1];
        System.arraycopy(givePowerTo, 0, newGPT, 0, givePowerTo.length);
        givePowerTo = newGPT;
        givePowerTo[givePowerTo.length - 1] = dest;
    }
}


