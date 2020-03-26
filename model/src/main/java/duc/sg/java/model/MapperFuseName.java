package duc.sg.java.model;

import java.util.function.Function;

class MapperFuseName implements Function<Fuse, String> {
    @Override
    public String apply(Fuse fuse) {
        return fuse.getName();
    }
}