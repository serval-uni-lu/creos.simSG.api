package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;

public class AllCycle {
    private AllCycle() {}

    public static Fuse[] circleFrom(Fuse start) {
        return new CycleFinder().circleFrom(start);
    }
}
