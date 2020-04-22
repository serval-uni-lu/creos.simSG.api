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

    private static boolean pathToSubs(Fuse start) {
        var visited = new HashSet<Fuse>();
        var nbHopsEntities = new HashMap<Entity, Integer>();

        return p_pathToSubs(start, visited, nbHopsEntities);
    }

    private static boolean p_pathToSubs(Fuse start, HashSet<Fuse> visited, Map<Entity, Integer> nbHopsEntities) {
        visited.add(start);
        var nbHops = nbHopsEntities.compute(start.getOwner(),
                (Entity key, Integer currVal) -> (currVal == null)? 1 : currVal+1
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
        if(!visited.contains(opp)) {
            isAccessible = p_pathToSubs(opp, visited, nbHopsEntities);
        }
        if(isAccessible) {
            return true;
        }

        // fuse in the same cabinet
        var owner = start.getOwner();
        for(var f: owner.getFuses()) {
            if(!visited.contains(f)) {
                isAccessible = p_pathToSubs(f, visited, nbHopsEntities);
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
                    if (neighbor.getPowerFrom() != null) {


                        int containsCurrent = 0;
                        for (var f : neighbor.getPowerFrom()) {
                            if (f.equals(current) || f.getOwner().equals(current.getOwner())) {
                                containsCurrent++;
//                                break;
                            }
                        }
                        if (containsCurrent == 0 || containsCurrent != neighbor.getPowerFrom().length) {
                            powerOrigins.add(neighbor);
                        }
                    } else if (pathToSubs(neighbor)) {
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
