package duc.sg.java.circle.all;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.*;

class DetectCircle {
    private static final int STD_DIST = 1;
    private static final int SAME_OWNER_DIST = 10; //to avoid path to cross too often an entity

    private DetectCircle(){}


    private static int incNewDist(Fuse current, Fuse neighbour) {
        if(current.getOwner().equals(neighbour.getOwner())) {
            return SAME_OWNER_DIST;
        }

        return STD_DIST;
    }

    /**
     * Retrieve the circle, if it exists, between two fuses.
     * The circle, if it exists, is composed of all fuses that belong to the shortest path from the first fuse to the
     * second. We use the Dijkstra's algorithm.
     *
     * @param start
     * @param end
     *
     * @return
     */
    static Optional<Circle> findCircle(Fuse start, Fuse end) {
        if(start == null || end == null || start.equals(end) || !start.getOwner().equals(end.getOwner())) {
            return Optional.empty();
        }

        final var distanceMap = new HashMap<Fuse, Integer>();
        final var toVisit = new PriorityQueue<Fuse>((o1, o2) -> {
            int fwDist1 = distanceMap.getOrDefault(o1, Integer.MAX_VALUE);
            int fwDist2 = distanceMap.getOrDefault(o2, Integer.MAX_VALUE);
            return Integer.compare(fwDist1, fwDist2);
        });
        var previousMap = new HashMap<Fuse, Fuse>();

        distanceMap.put(start, 0);
        toVisit.add(start);

        while (!toVisit.isEmpty()) {
            Fuse current = toVisit.poll();

            if(current.equals(end)) {
                break;//we reach the destination
            }

            if (current.equals(start) || !(current.getOwner() instanceof Substation)) { //we avoid going through substations
                current.getNeighbors()
                        .stream()
                        .filter(neighbor -> !current.equals(start) || !neighbor.equals(end)) //ignore the direct path from start to end
                        .filter(neighbor -> neighbor.equals(end) || !neighbor.getOwner().equals(start.getOwner())) //ignore fuses that are in the same entity as the source (except the final)
                        .filter(neighbor -> !distanceMap.containsKey(neighbor))
                        .forEachOrdered((Fuse neighbor) -> {
                            int newDistance = distanceMap.get(current) + incNewDist(current, neighbor);
                            if (newDistance < distanceMap.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                                toVisit.remove(neighbor);
                                distanceMap.put(neighbor, newDistance);
                                previousMap.put(neighbor, current);
                                toVisit.add(neighbor);
                            }
                        });
            }


        }

        return buildCycle(start, end, previousMap);
    }

    private static Optional<Circle> buildCycle(Fuse start, Fuse end, HashMap<Fuse, Fuse> previousMap) {
        var circle = new ArrayList<Fuse>();
        Fuse current = end;
        while (previousMap.containsKey(current)) {
            circle.add(current);
            current = previousMap.get(current);
        }
        circle.add(start);

        if(circle.size() == 1) {
            return Optional.empty();
        }

        return Optional.of(new CircleImp(circle.toArray(Fuse[]::new)));
    }


}
