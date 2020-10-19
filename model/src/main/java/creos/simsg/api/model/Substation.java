package creos.simsg.api.model;


public class Substation extends Entity {
    private SmartGrid grid;

    public Substation(String name) {
        super(name);
    }

    void setGrid(SmartGrid grid) {
        this.grid = grid;
    }

    public SmartGrid getGrid() {
        return grid;
    }

    @Override
    public boolean isDeadEnd() {
        return false;
    }

    @Override
    public boolean mightBeDeadEnd() {
        return false;
    }

    @Override
    public boolean isAlwaysDeadEnd() {
        return false;
    }
}
