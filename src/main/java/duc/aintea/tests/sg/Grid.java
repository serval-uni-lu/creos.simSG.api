package duc.aintea.tests.sg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid {
    List<Substation> substations;

    public void addSubstation(Substation... subs) {
        if(substations == null) {
            substations = new ArrayList<>(subs.length);
        }
        Collections.addAll(substations, subs);
    }

    public List<Substation> getSubstations() {
        return new ArrayList<>(substations);
    }

}
