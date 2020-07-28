package duc.sg.java.loadapproximator.uncertain.multisubs.bsrules;

import duc.sg.java.model.Entity;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;
import duc.sg.java.navigation.bfs.BFSEntity;

import java.util.*;

public class ExtractComponents {
    private ExtractComponents(){}


    public static Collection<List<Substation>> getConnectedSubstations(SmartGrid grid) {
        var processedSubs = new HashSet<Substation>();

        var res = new ArrayList<List<Substation>>();

        for(Substation current: grid.getSubstations()) {
            if(!processedSubs.contains(current)) {
                processedSubs.add(current);

                final var component = new HashSet<Substation>();
                component.add(current);

                BFSEntity.INSTANCE.navigate(current, (Entity entity) -> {
                    if(entity instanceof Substation) {
                        var casted = (Substation) entity;
                        component.add(casted);
                        processedSubs.add(casted);
                    }
                });

                res.add(new ArrayList<>(component));
            }
        }

        return res;
    }

}
