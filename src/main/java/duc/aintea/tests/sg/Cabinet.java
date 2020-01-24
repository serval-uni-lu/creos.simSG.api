package duc.aintea.tests.sg;

import java.util.HashSet;
import java.util.Set;

public class Cabinet extends Entity {
    public Cabinet(String name) {
        super(name);
    }

    @Override
    public boolean isDeadEnd() {
        if(fuses.size() == 1) {
            return true;
        }
        
        Set<String> seen = new HashSet<>(fuses.size());
        Entity opposite = fuses.get(0).getOpposite();
        seen.add(opposite.getName());
        for (int i = 1; i < fuses.size(); i++) {
            opposite = fuses.get(i).getOpposite();
            if(seen.add(opposite.getName())) {
                return false;
            }
        }

        return true;
    }
}
