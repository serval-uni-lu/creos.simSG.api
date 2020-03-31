package duc.sg.java.scenarios;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;

public abstract class Scenario {
    protected SmartGrid grid;

    Scenario(SmartGrid grid) {
        this.grid = grid;
    }

    public SmartGrid getGrid() {
        return grid;
    }

    public Substation getSubstation() {
        return grid.getSubstation(substationName()).get();
    }


    public abstract String substationName();
    public abstract Fuse[] extractFuses();
    public abstract Cable[] extractCables();

}
