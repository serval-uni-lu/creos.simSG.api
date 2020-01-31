package duc.aintea.tests.utils;

import duc.aintea.tests.sg.Fuse;

import java.util.*;

public class CycleDetection {

//    public static boolean detectCircle(Fuse start) {
//        var waitingFuses = new Stack<Fuse>();
//        waitingFuses.add(start);
//
//        var visitedFuses = new HashSet<Fuse>();
//
//        var circle = new Stack<Fuse>();
//
//        while(!waitingFuses.isEmpty()) {
//            var current = waitingFuses.pop();
//            var oppCurrent = current.getOpposite();
//            if(!visitedFuses.contains(current)) {
//                visitedFuses.add(current);
//                visitedFuses.add(oppCurrent);
////                System.out.println("Visiting: " + current.getName() + "; Circle?: " + oppCurrent.getOwner().equals(start.getOwner()));
//
//                if(oppCurrent.getOwner().equals(start.getOwner())) {
//                    return true;
//                }
//
//            }
//
//
//            var owner = oppCurrent.getOwner();
//            for (var f: owner.getFuses()) {
//                if(!visitedFuses.contains(f)) {
//                    waitingFuses.add(f);
//                }
//            }
//
//        }
//        System.out.println();
//
//
//       return false;
//    }

    private int lastIndex = 0;
    private Deque<Fuse> circle;
    private Map<Fuse, Integer> idxMap;
    private Map<Fuse, Integer> lowLinkMap;


    public CycleDetection() {
        circle = new ArrayDeque<>();
        idxMap = new HashMap<>();
        lowLinkMap = new HashMap<>();
    }

    public Fuse[] getEndCircle(Fuse start) {
        Fuse oppStart = start.getOpposite();


        idxMap.put(start, lastIndex);
        lowLinkMap.put(start, lastIndex);
        lastIndex++;
        circle.push(start);

        idxMap.put(oppStart, lastIndex);
        lowLinkMap.put(oppStart, lastIndex);
        lastIndex++;
        circle.push(oppStart);

        var owner = oppStart.getOwner();
        for(var f: owner.getFuses()) {
            if(!idxMap.containsKey(f)) {
                getEndCircle(f);
                lowLinkMap.put(start, Math.min(lowLinkMap.get(start), lowLinkMap.get(f)));
            } else if(circle.contains(f)) {
                lowLinkMap.put(start, Math.min(lowLinkMap.get(start), lowLinkMap.get(f)));
            }
        }

        if(lowLinkMap.get(start).equals(idxMap.get(start))) {
            Fuse f;
            var res = new ArrayList<Fuse>();
            do {
                f = circle.pop();
                res.add(f);
            }while(!f.equals(start));
            if(res.size() > 2 && start.getOwner().getFuses().size() >= 2 ) {
                return res.toArray(new Fuse[0]);
            }
        }
        return new Fuse[0];



    }
}
