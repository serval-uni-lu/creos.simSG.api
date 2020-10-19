package creos.simsg.api.navigation.bfs;

import creos.simsg.api.model.Entity;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;
import creos.simsg.api.navigation.Condition;
import creos.simsg.api.navigation.Navigate;
import creos.simsg.api.navigation.Actionner;

import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * Navigate through the grid and call the actioner for every entities
 */
public class BFSEntity implements Navigate<Entity> {
    public static final BFSEntity INSTANCE = new BFSEntity();

    private BFSEntity(){}

    @Override
    public void navigate(Substation substation, Actionner<Entity> actioner, Condition condition) {
        if(substation.getFuses().isEmpty()) {
            actioner.act(substation);
            return;
        }

        var waiting = new ArrayDeque<Entity>();
        var inWaitingList = new HashSet<Entity>();
        var visited = new HashSet<Entity>();

        waiting.add(substation);

        while (!waiting.isEmpty()) {
            Entity current = waiting.poll();
            visited.add(current);
            actioner.act(current);

            for(Fuse fuse: current.getFuses()) {
                Entity neighbor = fuse.getOpposite().getOwner();
                if(!visited.contains(neighbor) && !inWaitingList.contains(neighbor) && condition.evaluate(fuse)) {
                    waiting.add(neighbor);
                    inWaitingList.add(neighbor);
                }
            }

        }
    }

}
