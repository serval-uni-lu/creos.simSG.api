package creos.simsg.api.preprocessor.powerflow;

import creos.simsg.api.model.Entity;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;

import java.util.*;

class Utils {
    static boolean pathToSubs(Fuse start, Fuse origin) {
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

}
