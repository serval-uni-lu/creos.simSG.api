package duc.sg.java.loadapproximator.uncertain.multisubs.bsrules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

                current.extract(component, new Substation.Collector<Substation>() {
                    @Override
                    public void collect(Collection<Substation> collection, Fuse currentFuse) {
                        var owner = currentFuse.getOwner();
                        if(owner instanceof Substation) {
                            var casted = (Substation) owner;
                            component.add(casted);
                            processedSubs.add(casted);
                        }
                    }
                });

                res.add(new ArrayList<>(component));
            }
        }

        return res;
    }

}
