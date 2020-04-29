package duc.sg.java.cycle.all;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;

import java.util.*;

public class AllCycle {
    private int lastIndex = 0;
    private final Deque<Fuse> circle;
    private final Map<Fuse, Integer> idxMap;
    private final Map<Fuse, Integer> lowLinkMap;
    private Entity beginningEntity;


    public AllCycle() {
        circle = new ArrayDeque<>();
        idxMap = new HashMap<>();
        lowLinkMap = new HashMap<>();
    }

    public Fuse[] circleFrom(Fuse start) {
        Fuse oppStart = start.getOpposite();
        if(beginningEntity == null) {
            beginningEntity = start.getOwner();
        }

        idxMap.put(start, lastIndex);
        lowLinkMap.put(start, lastIndex);
        lastIndex++;
        circle.push(start);

        idxMap.put(oppStart, lastIndex);
        lowLinkMap.put(oppStart, lastIndex);
        lastIndex++;
        circle.push(oppStart);

        var owner = oppStart.getOwner();
        if(!owner.isAlwaysDeadEnd()) {
            for (var f : owner.getFuses()) {
                if (!idxMap.containsKey(f) && !owner.equals(beginningEntity)) {
                    circleFrom(f);
                    lowLinkMap.put(start, Math.min(lowLinkMap.get(start), lowLinkMap.get(f)));
                } else if (circle.contains(f)) {
                    lowLinkMap.put(start, Math.min(lowLinkMap.get(start), lowLinkMap.get(f)));
                }
            }
        }

        if(lowLinkMap.get(start).equals(idxMap.get(start))) {
            Fuse f;
            var res = new ArrayList<Fuse>();
            do {
                f = circle.pop();
                if(!f.equals(start) && start.getOwner().equals(f.getOwner())) {
                    res.add(0, f);
                } else {
                    res.add(f);
                }
            } while(!f.equals(start));
            if(res.size() > 2 && start.getOwner().getFuses().size() >= 2 ) {
                return res.toArray(new Fuse[0]);
            }
        }
        return new Fuse[0];
    }
}
