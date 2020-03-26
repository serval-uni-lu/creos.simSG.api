package duc.sg.java.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

public class Substation extends Entity {
    public Substation(String name) {
        super(name);
    }

    @Override
    public boolean isDeadEnd() {
        return false;
    }

    private <T> void extract(Collection<T> collection, Collector<T> collector) {

        var waiting = new Stack<Fuse>();
        var inWaitingList = new HashSet<Fuse>(); //real optimization ??
        var visited = new HashSet<Fuse>();

        waiting.add(this.getFuses().get(0));

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






    public Collection<Fuse> extractFuses() {
        final var res = new HashSet<Fuse>();
        extract(res, (collection, currentFuse) -> {
            if(!collection.contains(currentFuse)) {
                collection.add(currentFuse);
            }
        });
        return Collections.unmodifiableCollection(res);


        /*var waiting = new Stack<Fuse>();

        var visited = new HashSet<Fuse>();
        var added = new HashSet<Fuse>();

        waiting.add(this.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);

            var ownerOpp = current.getOpposite().getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f) && !added.contains(f)) {
                    waiting.add(f);
                    added.add(f);
                }
            }
        }

        return Collections.unmodifiableCollection(visited);*/
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

        /*var res = new ArrayList<Cable>();

        var waiting = new Stack<Fuse>();
        var visited = new HashSet<Fuse>();
        var added = new HashSet<Fuse>();

        waiting.add(this.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);

            if(visited.contains(current) && visited.contains(current.getOpposite())) {
                res.add(current.getCable());
            }

            var ownerOpp = current.getOpposite().getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f) && !added.contains(f) ) {
                    waiting.add(f);
                    added.add(f);
                }
            }
        }

        return Collections.unmodifiableCollection(res);*/
    }

    private interface Collector<T> {
        void collect(Collection<T> collection, Fuse currentFuse);
    }
}
