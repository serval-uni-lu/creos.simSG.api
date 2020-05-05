package duc.sg.java.model;

import duc.sg.java.uncertainty.Category;
import duc.sg.java.uncertainty.Confidence;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;

import java.util.Map;

public class Fuse {
    private String name;
    private Cable cable;
    private Entity owner;
    private Status status;
    private MultDblePossibilities uload;
    private Fuse[] getPowerFrom;
    private Fuse[] givePowerTo;


    public Fuse(String name) {
        this(name, State.CLOSED);
    }

    public Fuse(String name, State state) {
        this.name = name;
        this.status = new Status(state, 1.);
        this.givePowerTo = new Fuse[0];
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
        if(uload == null) {
//            var dft = new MultDblePossibilities();
//            dft.addOrReplace(new PossibilityDouble(0, Confidence.MAX_PROBABILITY));
//            uload = dft;
            uload = new MultDblePossibilities();
            uload.add(new PossibilityDouble(0, Confidence.MAX_PROBABILITY));
        }
        return uload;
    }

    public Map<Category, Double> formattedULoad() {
//        if(uload == null) {
////            var dft = new MultDblePossibilities();
////            dft.addOrReplace(new PossibilityDouble(0, Confidence.MAX_PROBABILITY));
////            return dft.format();
//        }
//        return uload.format();
        return getUncertainLoad().format();
    }

    public void setLoad(MultDblePossibilities uload) {
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


