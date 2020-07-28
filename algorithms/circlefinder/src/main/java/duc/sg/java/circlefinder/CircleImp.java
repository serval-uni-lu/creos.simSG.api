package duc.sg.java.circlefinder;

import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.utils.StringAccumlator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

class CircleImp implements Circle {
    Fuse[] fuses;

    public CircleImp(Fuse[] fuses) {
        this.fuses = fuses;
        Arrays.sort(fuses, Comparator.comparing(Fuse::getName));
    }

    @Override
    public Fuse[] getFuses() {
        return fuses;
    }

    @Override
    public boolean isEffective() {
        for(Fuse fuse: fuses) {
            if(!fuse.isClosed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEffective(Configuration configuration) {
        for (Fuse fuse: getFuses()) {
            if(!configuration.isClosed(fuse)) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircleImp circleImp = (CircleImp) o;


        return Arrays.equals(fuses, circleImp.fuses);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(fuses);
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
