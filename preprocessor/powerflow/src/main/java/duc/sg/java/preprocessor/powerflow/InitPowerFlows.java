package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class InitPowerFlows {
    private InitPowerFlows() {}


    public static void initPowerFlow(Substation substation) {
        Collection<Fuse> fuses = substation.extractFuses();

        for(Fuse fuse: fuses) {
            if(fuse.getOwner() instanceof Substation) {
                System.out.println(fuse.getName() + " -> ()");
            } else {
                System.out.println(fuse.getName());
                getPathToSubs(fuse);
            }




        }

    }

    public static void getPathToSubs(Fuse start) {
        final var path = new ArrayList<String>();
        path.add(start.getName());

        var visited = new HashSet<Fuse>();

        p_getpath(start, path, visited);

    }

    private static void p_getpath(Fuse start, ArrayList<String> path, HashSet<Fuse> visited) {
        visited.add(start);

        if(start.getOwner() instanceof Substation) {
            System.out.println("\t" + Arrays.toString(path.toArray(new String[0])));
            visited.remove(start);
            return;
        }

        // opposite fuse
        var opp = start.getOpposite();
        if(!visited.contains(opp)) {
            path.add(opp.getName());
            p_getpath(opp, path, visited);
            path.remove(opp.getName());
        }

        // fuse in the same cabinet
        var owner = start.getOwner();
        for(var f: owner.getFuses()) {
            if(!visited.contains(f)) {
                path.add(f.getName());
                p_getpath(f, path, visited);
                path.remove(f.getName());
            }
        }

        visited.remove(start);

    }

}
