package duc.sg.java.scenarios;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;

public abstract class Scenario {
    protected SmartGrid grid;

    protected Scenario(SmartGrid grid) {
        this.grid = grid;
    }

    public abstract Fuse[] extractFuses();
    public abstract Cable[] extractCables();

    public SmartGrid getGrid() {
        return grid;
    }
}
