package duc.sg.java.cycle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class CycleFinder3 {
    private static final int STD_DIST = 1;
    private static final int SAME_OWNER_DIST = 10; //to avoid path to cross too often an entity

    private CycleFinder3(){}

    private static int incNewDist(Fuse current, Fuse neighbour) {
        if(current.getOwner().equals(neighbour.getOwner())) {
            return SAME_OWNER_DIST;
        }

        return STD_DIST;
    }

    public static Fuse[] findCycle(Fuse start, Fuse end) {
        if(start == null || end == null || start.equals(end) || !start.getOwner().equals(end.getOwner())) {
            return new Fuse[0];
        }

        final var distanceMap = new HashMap<Fuse, Integer>();
        var toVisit = new PriorityQueue<Fuse>((o1, o2) -> {
            int fwDist1 = distanceMap.getOrDefault(o1, Integer.MAX_VALUE);
            int fwDist2 = distanceMap.getOrDefault(o2, Integer.MAX_VALUE);
            return Integer.compare(fwDist1, fwDist2);
        });

        var visited = new HashSet<Fuse>();
        var previousMap = new HashMap<Fuse, Fuse>();

        distanceMap.put(start, 0);

        toVisit.add(start);

        while (!toVisit.isEmpty()) {
            Fuse current = toVisit.poll();
            visited.add(current);

            if(current.equals(end)) {
                break;//we reach the destination
            }

            if(!current.equals(start) && (current.getOwner() instanceof Substation)) {
                continue;
            }

            for(Fuse neighbor: current.getNeighbors()) {
                if(current.equals(start) && neighbor.equals(end)) {
                    continue; //ignore the direct path from start to end
                }

                if(!neighbor.equals(end) && neighbor.getOwner().equals(start.getOwner())) {
                    continue; //ignore fuses that are in the same entity as the source and target
                }

                if(!visited.contains(neighbor)) {
                    int newDistance = distanceMap.get(current) + incNewDist(current, neighbor);
                    if(newDistance < distanceMap.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        toVisit.remove(neighbor);
                        distanceMap.put(neighbor, newDistance);
                        previousMap.put(neighbor, current);
                        toVisit.add(neighbor);
                    }
                }
            }

        }

        // construct the cycle
        var cycle = new ArrayList<Fuse>();
        Fuse current = end;
        while (previousMap.containsKey(current)) {
            cycle.add(current);
            current = previousMap.get(current);
        }
        cycle.add(start);

        if(cycle.size() == 1) {
            return  new Fuse[0];
        }

        return cycle.toArray(new Fuse[0]);
    }


}
