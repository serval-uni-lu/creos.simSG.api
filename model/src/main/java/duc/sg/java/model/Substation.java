package duc.sg.java.model;


import java.util.*;

public class Substation extends Entity {
    private Collection<Fuse> allFuses;
//    private Collection<Fuse[]> cycles;
    private SmartGrid grid;



    public Substation(String name) {
        super(name);
    }

    public Collection<Fuse> getAllFuses() {
        return allFuses;
    }

//    public Collection<Fuse[]> getCycles() {
//        return cycles;
//    }
//
//    public void setCycles(Collection<Fuse[]> cycles) {
//        this.cycles = cycles;
//    }

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

    public <T> void extract(Collection<T> collection, Collector<T> collector) {
        var waiting = new Stack<Fuse>();
        var inWaitingList = new HashSet<Fuse>(); //real optimization ??
        var visited = new HashSet<Fuse>();

        if(!this.getFuses().isEmpty()) {
            waiting.add(this.getFuses().get(0));
        }

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);

            collector.collect(collection, current);

            var ownerOpp = current.getOpposite().getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f) && !inWaitingList.contains(f)) {
                    waiting.add(f);
                    inWaitingList.add(f);
                }
            }
        }
    }

    public void updateAllFuses() {
        allFuses = extractFuses();
    }

    public Collection<Fuse> extractFuses() {
        final var res = new HashSet<Fuse>();
        extract(res, (collection, currentFuse) -> {
            if(!collection.contains(currentFuse)) {
                collection.add(currentFuse);
            }
        });
        return Collections.unmodifiableCollection(res);
    }

    public Collection<Cable> extractCables() {
        final var res = new HashSet<Cable>();
        extract(res, (collection, currentFuse) -> {
            Cable cable = currentFuse.getCable();
            if(!collection.contains(cable)) {
                collection.add(cable);
            }
        });
        return Collections.unmodifiableCollection(res);
    }



    public interface Collector<T> {
        void collect(Collection<T> collection, Fuse currentFuse);
    }

    void setGrid(SmartGrid grid) {
        this.grid = grid;
    }

    public SmartGrid getGrid() {
        return grid;
    }
}
