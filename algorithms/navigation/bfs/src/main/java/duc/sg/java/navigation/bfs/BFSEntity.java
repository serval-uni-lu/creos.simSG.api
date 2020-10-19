package duc.sg.java.navigation.bfs;

import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.Actionner;
import duc.sg.java.navigation.Condition;
import duc.sg.java.navigation.Navigate;

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
