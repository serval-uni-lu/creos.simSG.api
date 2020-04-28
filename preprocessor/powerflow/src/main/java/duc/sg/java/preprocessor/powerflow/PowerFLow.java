package duc.sg.java.preprocessor.powerflow;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;

import java.util.*;

public class PowerFLow implements IPowerFlow {
    private Map<Fuse, Fuse[]> powerSrc;
    private Map<Fuse, Fuse[]> powerDest;

    public PowerFLow() {
        this.powerDest = new HashMap<>();
        this.powerSrc = new HashMap<>();
    }

    private void setPowerFlow(Fuse current, List<Fuse> srcs) {
        powerSrc.put(current, srcs.toArray(new Fuse[0]));

        for(Fuse src: srcs) {
            powerDest.compute(src, (Fuse key, Fuse[] currentDest) -> {
                if (currentDest == null) {
                    return new Fuse[]{current};
                }
                Fuse[] newDest = new Fuse[currentDest.length + 1];
                System.arraycopy(currentDest, 0, newDest, 0, currentDest.length);
                newDest[currentDest.length] = current;
                return newDest;
            });
        }


    }


    private void initPowerFLow(Substation substation) {
        var waiting = new Stack<Fuse>();
        var inWaitingList = new HashSet<Fuse>(); //real optimization ??
        var visited = new HashSet<Fuse>();

        waiting.add(substation.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            visited.add(current);

            var powerOrigins = new ArrayList<Fuse>();
            if(!(current.getOwner() instanceof Substation)) {
                List<Fuse> neighbors = Utils.getNeighbors(current);
                for (var neighbor : neighbors) {
                    if (Utils.pathToSubs(neighbor, current)) {
                        powerOrigins.add(neighbor);
                    }
                }



            }
            setPowerFlow(current, powerOrigins);


            // next fuses to check
            var ownerOpp = current.getOpposite().getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!visited.contains(f) && !inWaitingList.contains(f)) {
                    waiting.add(f);
                    inWaitingList.add(f);
                }
            }
        }
    }


    @Override
    public Fuse[] getFuseOnMandatoryPF(Substation substation) {
        initPowerFLow(substation);
        Set<Fuse> shouldBeOpen = new HashSet<>();

        for(Map.Entry<Fuse, Fuse[]> mapEntry: powerSrc.entrySet()) {
            Fuse current = mapEntry.getKey();
            if(!(current.getOwner() instanceof Substation)) {
                Fuse[] currPwSrc = mapEntry.getValue();
                Fuse[] currPwDest = powerDest.get(current);

                var filtered = new ArrayList<Fuse>(currPwSrc.length);
                for(Fuse s: currPwSrc) {
                    if(!(s.getOwner() instanceof Substation) && !Utils.contain(currPwDest, s)) {
                        filtered.add(s);
                    }
                }
                if(filtered.size() == 1) {
                    shouldBeOpen.add(filtered.get(0));
                }

            }
        }



        return shouldBeOpen.toArray(new Fuse[0]);
    }
}
