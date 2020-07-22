package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.utils.StringAccumlator;

import java.util.stream.Stream;

class CycleImp implements Cycle {
    Fuse[] fuses;

    public CycleImp(Fuse[] fuses) {
        this.fuses = fuses;
    }

    @Override
    public Fuse[] getFuses() {
        return fuses;
    }

    @Override
    public boolean isValid() {
        for(Fuse fuse: fuses) {
            if(!fuse.isClosed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Fuse getOtherEndPoint(Fuse start) {
        for (int i = 0; i < fuses.length; i++) {
            Fuse fc = fuses[i];
            if (!fc.equals(start) && fc.getOwner().equals(start.getOwner()) && i>0) {
                return fc;
            }
        }
        return null; //should never be executed
    }

    @Override
    public String toString() {
        String fuseNames = Stream.of(fuses)
                .map(Fuse::getName)
                .reduce("", StringAccumlator.INSTANCE);

        return "Cycle(" +
                "fuses=" + fuseNames +
                ')';
    }
}
