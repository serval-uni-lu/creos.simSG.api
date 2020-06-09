package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.cycle.all.InitAllCycleSubs2;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.*;

public class PowerFlow2 implements IPowerFlow {
    @Override
    public Fuse[] getFuseOnMandatoryPF(Substation substation) {
        InitAllCycleSubs2.init(substation);
        if(substation.getAllFuses() == null) {
            substation.updateAllFuses();
        }


        Collection<Fuse[]> circles = substation.getCycles();
        Collection<Fuse> allFuses = substation.getAllFuses();

        var fusesOnCircles = new HashSet<Fuse>();

        for(Fuse[] circle: circles) {
            Collections.addAll(fusesOnCircles, circle);
        }

        List<Fuse> mpf = new ArrayList<>();
        for(Fuse f: allFuses) {
            if(!fusesOnCircles.contains(f) && !f.getOwner().isAlwaysDeadEnd()) {
                mpf.add(f);
            }
        }


        return mpf.toArray(new Fuse[0]);
    }
}
