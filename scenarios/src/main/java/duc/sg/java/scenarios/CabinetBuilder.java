package duc.sg.java.scenarios;

import duc.aintea.sg.*;

public class CabinetBuilder {
    public static final String F1_NAME = "fuse_subs";
    public static final String F2_NAME = "fuse1_cabinet_1";
    public static final String F3_NAME = "fuse2_cabinet_1";
    public static final String F4_NAME = "fuse3_cabinet_1";
    public static final String F5_NAME = "fuse_cabinet2";
    public static final String F6_NAME = "fuse_cabinet3";

    /*
                                          |-[f3]----(cbl2)----[f5]-cab2
        subs-[f1]----(cbl1)----[f2]-cab1-<
                                          |-[f4]----(cbl3)----[f6]-cab3
     */
    public static Substation build() {
       return build(new boolean[]{true, true, true, true, true, true}, new double[3]);
    }

    public static Substation build(boolean[] fuseClose, double[] consumptions) {
        var subs = new Substation("substation");
        var cab1 = new Cabinet("cabinet1");
        var cab2 = new Cabinet("cabinet1");
        var cab3 = new Cabinet("cabinet1");

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
        var f3 = new Fuse(F3_NAME);
        if(!fuseClose[2]) {
            f3.openFuse();
            f3.getStatus().makeCertain();
        }
        var f4 = new Fuse(F4_NAME);
        if(!fuseClose[3]) {
            f4.openFuse();
            f4.getStatus().makeCertain();
        }
        var f5 = new Fuse(F5_NAME);
        if(!fuseClose[4]) {
            f5.openFuse();
            f5.getStatus().makeCertain();
        }
        var f6 = new Fuse(F6_NAME);
        if(!fuseClose[5]) {
            f6.openFuse();
            f6.getStatus().makeCertain();
        }

        var cbl1 = new Cable();
        var m1 = new Meter("m1");
        m1.setConsumption(consumptions[0]);
        cbl1.addMeters(m1);
        var cbl2 = new Cable();
        var m2 = new Meter("m1");
        m2.setConsumption(consumptions[1]);
        cbl2.addMeters(m2);
        var cbl3 = new Cable();
        var m3 = new Meter("m3");
        m3.setConsumption(consumptions[2]);
        cbl3.addMeters(m3);

        subs.addFuses(f1);
        cab1.addFuses(f2, f3, f4);
        cab2.addFuses(f5);
        cab3.addFuses(f6);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f5);
        cbl3.setFuses(f4, f6);

        return subs;
    }

    public static Fuse[] extractFuses(Substation substation) {
        var res = new Fuse[6];
        res[0] = substation.getFuses().get(0);
        res[1] = res[0].getOpposite();

        var cabinet = res[1].getOwner();
        res[2] = cabinet.getFuses().get(1);
        res[3] = cabinet.getFuses().get(2);

        res[4] = res[2].getOpposite();
        res[5] = res[3].getOpposite();

        return res;
    }

    public static Cable[] extractCables(Substation substation) {
        var res = new Cable[3];

        var fuses = extractFuses(substation);
        res[0] = fuses[0].getCable();
        res[1] = fuses[2].getCable();
        res[2] = fuses[3].getCable();

        return res;
    }
}
