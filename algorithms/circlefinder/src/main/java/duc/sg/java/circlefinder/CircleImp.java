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
        return Arrays.stream(fuses).allMatch(Fuse::isClosed);

    }

    @Override
    public boolean isEffective(Configuration configuration) {
        return Arrays.stream(fuses).allMatch(configuration::isClosed);
    }

    @Override
    public Fuse getOtherEndPoint(Fuse start) {
        return Arrays.stream(fuses)
                .filter((Fuse fuse) -> !fuse.equals(start) && fuse.getOwner().equals(start.getOwner()))
                .findFirst()
                .orElse(null);
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
