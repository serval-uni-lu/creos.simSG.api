package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.*;

public class InitPowerFlows2 {
    private InitPowerFlows2(){}

    private static List<Fuse> getNeighbors(Fuse start) {
        var res = new ArrayList<Fuse>();

        res.add(start.getOpposite());
        for(var f: start.getOwner().getFuses()) {
            if(!f.equals(start)) {
                res.add(f);
            }
        }

        return res;
    }

    private static boolean pathToSubs(Fuse start, Fuse origin) {
        var visited = new HashSet<Fuse>();
        var nbHopsEntities = new HashMap<Entity, Integer>();

        return p_pathToSubs(start, visited, nbHopsEntities, origin, start.getOwner().equals(origin.getOwner()));
    }

    private static boolean p_pathToSubs(Fuse start, HashSet<Fuse> visited, Map<Entity, Integer> nbHopsEntities, Fuse origin, boolean sameEntity) {
        visited.add(start);
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


        if(nbHops > 1) {
            visited.remove(start);
            nbHopsEntities.compute(start.getOwner(), (Entity key, Integer currVal) -> currVal-1);
            return false;
        }

        if(start.getOwner() instanceof Substation) {
            visited.remove(start);
            nbHopsEntities.compute(start.getOwner(), (Entity key, Integer currVal) -> currVal-1);
            return true;
        }

        if(start.getOwner().isAlwaysDeadEnd()) {
            visited.remove(start);
            nbHopsEntities.compute(start.getOwner(), (Entity key, Integer currVal) -> currVal-1);
            return false;
        }



        // Opposite fuse
        var opp = start.getOpposite();
        boolean isAccessible = false;
        if(!opp.equals(origin)) {
            if(!visited.contains(opp)) {
                isAccessible = p_pathToSubs(opp, visited, nbHopsEntities, origin, opp.getOwner().equals(start.getOwner()));
            }
            if(isAccessible) {
                return true;
            }
        }

        // fuse in the same cabinet
        var owner = start.getOwner();
        for(var f: owner.getFuses()) {
            if(f.equals(origin)) {
                continue;
            }
            if(!visited.contains(f)) {
                isAccessible = p_pathToSubs(f, visited, nbHopsEntities, origin, f.getOwner().equals(start.getOwner()));
                if(isAccessible) {
                    return true;
                }
            }
        }

        visited.remove(start);
        nbHopsEntities.compute(start.getOwner(), (Entity key, Integer currVal) -> currVal-1);
        return false;
    }


    public static void initPowerFlow(Substation substation) {
        var waiting = new Stack<Fuse>();
        var inWaitingList = new HashSet<Fuse>(); //real optimization ??
        var visited = new HashSet<Fuse>();

        waiting.add(substation.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);

            var powerOrigins = new ArrayList<Fuse>();
            if(!(current.getOwner() instanceof Substation)) {
                List<Fuse> neighbors = getNeighbors(current);
                for (var neighbor : neighbors) {
                    if (pathToSubs(neighbor, current)) {
                        powerOrigins.add(neighbor);
                    }
                }



            }
            current.setPowerFrom(powerOrigins.toArray(new Fuse[0]));


            // next fuses to check
            var ownerOpp = current.getOpposite().getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f) && !inWaitingList.contains(f)) {
                    waiting.add(f);
                    inWaitingList.add(f);
                }
            }
        }
    }

}
