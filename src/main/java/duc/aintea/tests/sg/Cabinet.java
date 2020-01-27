package duc.aintea.tests.sg;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cabinet extends Entity {
    public Cabinet(String name) {
        super(name);
    }

    @Override
    //TODO FIX
    //definitions of dead end and Fuse#opposite create "infinite loop"
    public boolean isDeadEnd() {
        List<Entity> neighboors = getReachableEntities();

        if(neighboors.size() == 1) {
            return true;
        }

        Set<String> names = new HashSet<>(neighboors.size());
        for (int i = 0; i < neighboors.size(); i++) {
            Entity entity = neighboors.get(i);
            if (names.add(entity.getName()) && i>0) {
                return false;
            }
        }

        return true;
    }
}
