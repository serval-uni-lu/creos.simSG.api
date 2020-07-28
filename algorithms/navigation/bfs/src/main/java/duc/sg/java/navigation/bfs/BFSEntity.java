package duc.sg.java.navigation.bfs;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.Actionner;
import duc.sg.java.navigation.Navigate;

import java.util.ArrayDeque;
import java.util.HashSet;

public class BFSEntity implements Navigate<Entity> {
    public static final BFSEntity INSTANCE = new BFSEntity();

    private BFSEntity(){}

    public void navigate(Substation substation, Actionner<Entity> actionner) {
        if(substation.getFuses().isEmpty()) {
            actionner.act(substation, new HashSet<>());
            return;
        }

        var waiting = new ArrayDeque<Entity>();
        var inWaitingList = new HashSet<Entity>();
        var visited = new HashSet<Entity>();

        waiting.add(substation);

        while (!waiting.isEmpty()) {
            Entity current = waiting.poll();
            visited.add(current);
            actionner.act(current, visited);

           for(Fuse fuse: current.getFuses()) {
               Entity neighbor = fuse.getOpposite().getOwner();
               if(!visited.contains(neighbor) && !inWaitingList.contains(neighbor)) {
                   waiting.add(neighbor);
                   inWaitingList.add(neighbor);
               }
           }

        }

    }

    public void navigate(Substation substation, Actionner<Entity> actionner, Condition<Fuse, Entity> condition) {
        if(substation.getFuses().isEmpty()) {
            actionner.act(substation, new HashSet<>());
            return;
        }

        var waiting = new ArrayDeque<Entity>();
        var inWaitingList = new HashSet<Entity>();
        var visited = new HashSet<Entity>();

        waiting.add(substation);

        while (!waiting.isEmpty()) {
            Entity current = waiting.poll();
            visited.add(current);
            actionner.act(current, visited);

            for(Fuse fuse: current.getFuses()) {
                Entity neighbor = fuse.getOpposite().getOwner();
                if(!visited.contains(neighbor) && !inWaitingList.contains(neighbor) && condition.evaluate(fuse, visited)) {
                    waiting.add(neighbor);
                    inWaitingList.add(neighbor);
                }
            }

        }
    }

}
