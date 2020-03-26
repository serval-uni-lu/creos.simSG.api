package duc.sg.java.scenarios;

import duc.aintea.sg.*;

public class SingleCableBuilder {
    public static final String F1_NAME = "fuse_subs";
    public static final String F2_NAME = "fuse_cabinet";

    /*
                subs-[f1]----(cbl1)----[f2]-cab
             */
    public static Substation build() {
        return build(new boolean[]{true, true}, new double[1]);
    }


    public static Substation build(boolean[] fuseClose, double[] consumptions) {
        var subs = new Substation("substation");
        var cab = new Cabinet("cabinet");

        var f1 = new Fuse(F1_NAME);
        if(!fuseClose[0]) {
            f1.openFuse();
            f1.getStatus().makeCertain();
        }
        var f2 = new Fuse(F2_NAME);
        if(!fuseClose[1]) {
            f2.openFuse();
            f2.getStatus().makeCertain();
        }

        var cable = new Cable();
        var meter = new Meter("m1");
        meter.setConsumption(consumptions[0]);
        cable.addMeters(meter);

        subs.addFuses(f1);
        cab.addFuses(f2);

        cable.setFuses(f1, f2);

        return subs;
    }

    public static Fuse[] extractFuses(Substation substation) {
        var res = new Fuse[2];
        res[0] = substation.getFuses().get(0);
        res[1] = res[0].getOpposite();
        return res;
    }

    public static Cable[] extractCables(Substation substation) {
        return new Cable[]{substation.getFuses().get(0).getCable()};
    }

}
