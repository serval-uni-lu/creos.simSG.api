package creos.simsg.api.scenarios;

import creos.simsg.api.model.Cable;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.SmartGrid;
import creos.simsg.api.model.Substation;

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
