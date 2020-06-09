package duc.sg.java.cycle.all;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;

import java.util.*;

class CycleFinder {
    private int lastIndex = 0;
    private final Deque<Fuse> circle;
    private final Map<Fuse, Integer> idxMap;
    private final Map<Fuse, Integer> lowLinkMap;
    private Entity beginningEntity;


    public CycleFinder() {
        circle = new ArrayDeque<>();
        idxMap = new HashMap<>();
        lowLinkMap = new HashMap<>();
    }

    private Fuse[] p_circleFrom(Fuse start, Map<Entity, Integer> nbHopsEntities, boolean sameEntity) {
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

        var nbHops = nbHopsEntities.compute(start.getOwner(),
                (Entity key, Integer currVal) -> {
                    if (currVal == null) {
                        if (sameEntity) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                    return currVal + 1;
                }
        );

        var owner = oppStart.getOwner();
        if(!owner.isAlwaysDeadEnd() && nbHops <= 1) {
            for (var f : owner.getFuses()) {
                if (!idxMap.containsKey(f) && !owner.equals(beginningEntity)) {
//                    circleFrom(f);
                    p_circleFrom(f, nbHopsEntities, owner.equals(start.getOwner()));
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
                nbHopsEntities.compute(f.getOwner(), (Entity key, Integer currVal) -> {
                    if(currVal == null) {
                        return null;
                    }
                    return currVal==0? currVal : currVal-1;
                });
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

    public Fuse[] circleFrom(Fuse start) {
        return p_circleFrom(start, new HashMap<>(), false);

//        Fuse oppStart = start.getOpposite();
//        if(beginningEntity == null) {
//            beginningEntity = start.getOwner();
//        }
//
//        idxMap.put(start, lastIndex);
//        lowLinkMap.put(start, lastIndex);
//        lastIndex++;
//        circle.push(start);
//
//        idxMap.put(oppStart, lastIndex);
//        lowLinkMap.put(oppStart, lastIndex);
//        lastIndex++;
//        circle.push(oppStart);
//
//        var owner = oppStart.getOwner();
//        if(!owner.isAlwaysDeadEnd()) {
//            for (var f : owner.getFuses()) {
//                if (!idxMap.containsKey(f) && !owner.equals(beginningEntity)) {
//                    circleFrom(f);
//                    lowLinkMap.put(start, Math.min(lowLinkMap.get(start), lowLinkMap.get(f)));
//                } else if (circle.contains(f)) {
//                    lowLinkMap.put(start, Math.min(lowLinkMap.get(start), lowLinkMap.get(f)));
//                }
//            }
//        }
//
//        if(lowLinkMap.get(start).equals(idxMap.get(start))) {
//            Fuse f;
//            var res = new ArrayList<Fuse>();
//            do {
//                f = circle.pop();
//                if(!f.equals(start) && start.getOwner().equals(f.getOwner())) {
//                    res.add(0, f);
//                } else {
//                    res.add(f);
//                }
//            } while(!f.equals(start));
//            if(res.size() > 2 && start.getOwner().getFuses().size() >= 2 ) {
//                return res.toArray(new Fuse[0]);
//            }
//        }
//        return new Fuse[0];
    }
}
