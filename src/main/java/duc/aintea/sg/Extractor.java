package duc.aintea.sg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Extractor {
    private Extractor(){}

    public static List<Fuse> extractFuses(Substation substation) {
        var res = new ArrayList<Fuse>();

        var waiting = new Stack<Fuse>();
        var visited = new HashSet<Fuse>();

        waiting.add(substation.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);
            res.add(current);

            var opp = current.getOpposite();
            visited.add(opp);
            res.add(opp);

            var ownerOpp = opp.getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f)) {
                    waiting.add(f);
                }
            }
        }

        return res;
    }

    public static List<Cable> extractCables(Substation substation) {
        var res = new ArrayList<Cable>();

        var waiting = new Stack<Fuse>();
        var visited = new HashSet<Fuse>();

        waiting.add(substation.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);
            res.add(current.getCable());

            var opp = current.getOpposite();
            visited.add(opp);

            var ownerOpp = opp.getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f)) {
                    waiting.add(f);
                }
            }
        }

        return res;
    }
}
