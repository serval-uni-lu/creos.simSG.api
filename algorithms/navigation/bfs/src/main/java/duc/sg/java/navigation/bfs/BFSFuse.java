package duc.sg.java.navigation.bfs;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.Actionner;
import duc.sg.java.navigation.Navigate;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class BFSFuse implements Navigate<Fuse> {
    public static final BFSFuse INSTANCE = new BFSFuse();

    private BFSFuse(){}

    public void navigate(Substation substation, Actionner<Fuse> actionner) {
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
                if(!visited.contains(f) && !inWaitingList.contains(f)) {
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
