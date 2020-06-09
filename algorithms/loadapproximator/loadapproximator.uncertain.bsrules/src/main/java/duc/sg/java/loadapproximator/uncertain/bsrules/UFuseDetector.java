package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.validator.rules.Rules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UFuseDetector {

    private UFuseDetector() {}

    private static Map<Fuse, State> generateMap(Collection<Fuse> fuses) {
        return generateMap(fuses.toArray(new Fuse[0]));
    }

    private static Map<Fuse, State> generateMap(Fuse... fuses) {
        var res = new HashMap<Fuse, State>();

        for(Fuse f: fuses) {
            State state;
            if(f.getStatus().isCertain()) {
                state = f.getStatus().getState();
            } else {
                state = State.CLOSED;
            }
            res.put(f, state);
        }


        return res;
    }

    private static void closeFuses(Collection<Fuse> fuses) {
        for(Fuse f: fuses) {
            f.closeFuse();
        }
    }

    public static final void detectAndModifyUFuses(Substation substation) {
        Collection<Fuse> fuses = substation.getAllFuses();
        if(fuses == null) {
            substation.updateAllFuses();
            fuses = substation.getAllFuses();
        }

        //Substation rule
        {
            Collection<Fuse> subsFuses = substation.getFuses();
            Map<Fuse, State> stateMap = generateMap(subsFuses);
            if(!Rules.getSubstationRule().apply(substation, stateMap)) {
                closeFuses(subsFuses);
            }
        }



        for (Fuse fuse: fuses) {
            //Rule Cable

        }




    }

}
