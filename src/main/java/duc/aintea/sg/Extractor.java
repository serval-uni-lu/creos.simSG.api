package duc.aintea.sg;

import java.util.*;

public class Extractor {
    private Extractor(){}

    public static Collection<Fuse> extractFuses(Substation substation) {
        var waiting = new Stack<Fuse>();

        var visited = new HashSet<Fuse>();
        var added = new HashSet<Fuse>();

        waiting.add(substation.getFuses().get(0));

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

        return visited;
    }

    public static List<Cable> extractCables(Substation substation) {
        var res = new ArrayList<Cable>();

        var waiting = new Stack<Fuse>();
        var visited = new HashSet<Fuse>();
        var added = new HashSet<Fuse>();

        waiting.add(substation.getFuses().get(0));

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

        return res;
    }
}
