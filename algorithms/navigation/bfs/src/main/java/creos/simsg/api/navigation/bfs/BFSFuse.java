package creos.simsg.api.navigation.bfs;

import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;
import creos.simsg.api.navigation.Condition;
import creos.simsg.api.navigation.Navigate;
import creos.simsg.api.navigation.Actionner;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Navigate through the grid and call the actioner for every fuses
 */
public class BFSFuse implements Navigate<Fuse> {
    public static final BFSFuse INSTANCE = new BFSFuse();

    private BFSFuse(){}

    @Override
    public void navigate(Substation substation, Actionner<Fuse> actionner, Condition condition) {
        if(substation.getFuses().isEmpty()) {
            return;
        }

        var waiting = new ArrayDeque<>(substation.getFuses());
        var inWaitingList = new HashSet<Fuse>(); //real optimization ??
        var visited = new HashSet<Fuse>();

        while (!waiting.isEmpty()) {
            var current = waiting.poll();
            visited.add(current);
            actionner.act(current);

            var ownerOpp = current.getOpposite().getOwner();
            var toAdd = new ArrayList<Fuse>(ownerOpp.getFuses().size());
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f) && !inWaitingList.contains(f) && condition.evaluate(f)) {
                    if(f.equals(current.getOpposite())) {
                        toAdd.add(0, f);
                    } else {
                        toAdd.add(f);
                    }
                }
            }
            waiting.addAll(toAdd);
            inWaitingList.addAll(toAdd);
        }
    }
}
