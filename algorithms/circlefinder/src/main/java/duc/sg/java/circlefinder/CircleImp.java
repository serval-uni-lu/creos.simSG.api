package duc.sg.java.circlefinder;

import duc.sg.java.model.Fuse;
import duc.sg.java.utils.StringAccumlator;

import java.util.stream.Stream;

class CircleImp implements Circle {
    Fuse[] fuses;

    public CircleImp(Fuse[] fuses) {
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
        for (Fuse fc : fuses) {
            if (!fc.equals(start) && fc.getOwner().equals(start.getOwner())) {
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
