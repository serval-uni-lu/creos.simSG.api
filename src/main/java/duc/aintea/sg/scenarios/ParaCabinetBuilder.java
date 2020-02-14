package duc.aintea.sg.scenarios;

import duc.aintea.sg.*;

public class ParaCabinetBuilder {
    public static final  String F1_NAME = "fuse_subs";

    public static final  String F2_NAME = "fuse1_cabinet_1";
    public static final  String F3_NAME = "fuse2_cabinet_1";
    public static final  String F5_NAME = "fuse3_cabinet_1";

    public static final  String F4_NAME = "fuse1_cabinet_2";
    public static final  String F6_NAME = "fus2_cabinet_2";
    public static final  String F7_NAME = "fus3_cabinet_2";

    public static final  String F8_NAME = "fuse_cabinet_3";



    /*
                                        |-[f3]----(cbl2)----[f4]-|
        subs-[f1]----(cbl1)----[f2]-c1-<                          >-c2-[f7]----(cbl4)---[f8]-c3
                                        |-[f5]----(cbl3)----[f6]-|
     */
    public static Substation build() {
        return build(new boolean[]{true, true, true, true, true, true, true, true}, new double[]{0., 0., 0., 0.});

    }

    public static Substation build(boolean[] fuseClose, double[] consumptions) {
        var subs = new Substation("substation");
        var c1 = new Cabinet("c1");
        var c2 = new Cabinet("c2");
        var c3 = new Cabinet("c3");

        var cbl1 = new Cable();
        var m1 = new Meter("m1");
        m1.setConsumption(consumptions[0]);
        cbl1.addMeters(m1);
        var cbl2 = new Cable();
        var m2 = new Meter("m2");
        m2.setConsumption(consumptions[1]);
        cbl2.addMeters(m2);
        var cbl3 = new Cable();
        var m3 = new Meter("m3");
        m3.setConsumption(consumptions[2]);
        cbl3.addMeters(m3);
        var cbl4 = new Cable();
        var m4 = new Meter("m4");
        m4.setConsumption(consumptions[3]);
        cbl4.addMeters(m4);

        var f1 = new Fuse(F1_NAME);
        if(!fuseClose[0]) {
            f1.openFuse();
        }
        var f2 = new Fuse(F2_NAME);
        if(!fuseClose[1]) {
            f2.openFuse();
        }
        var f3 = new Fuse(F3_NAME);
        if(!fuseClose[2]) {
            f3.openFuse();
        }
        var f4 = new Fuse(F4_NAME);
        if(!fuseClose[3]) {
            f4.openFuse();
        }
        var f5 = new Fuse(F5_NAME);
        if(!fuseClose[4]) {
            f5.openFuse();
        }
        var f6 = new Fuse(F6_NAME);
        if(!fuseClose[5]) {
            f6.openFuse();
        }
        var f7 = new Fuse(F7_NAME);
        if(!fuseClose[6]) {
            f7.openFuse();
        }
        var f8 = new Fuse(F8_NAME);
        if(!fuseClose[7]) {
            f8.openFuse();
        }

        subs.addFuses(f1);
        c1.addFuses(f2, f3, f5);
        c2.addFuses(f4, f6, f7);
        c3.addFuses(f8);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);

        return subs;
    }
}
