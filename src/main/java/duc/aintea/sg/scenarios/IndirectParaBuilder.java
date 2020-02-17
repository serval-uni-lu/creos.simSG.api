package duc.aintea.sg.scenarios;

import duc.aintea.sg.*;

public class IndirectParaBuilder {
    public static final String F1_NAME = "fuse1_subs";
    public static final String F3_NAME = "fuse2_subs";

    public static final String F4_NAME = "fuse1_cab_1";
    public static final String F5_NAME = "fuse2_cab_1";
    public static final String F7_NAME = "fuse3_cab_1";


    public static final String F2_NAME = "fuse1_cab_2";
    public static final String F8_NAME = "fuse2_cab_2";
    public static final String F9_NAME = "fuse3_cab_2";


    public static final String F6_NAME = "fuse_cab_3";

    public static final String F10_NAME = "fuse_cab_4";







    /*
                                      c3
                                      |
                                     [f6]
                                      |
                                   (cbl3)
                                      |
                                     [f5]
                                      |
                                     _c1_
                                    /    \
              |-[f3]----(cbl2)----[f4]  [f7]----(cbl4)----[f8]-|
        subs-<                                                  >-c2-[f9]----(cbl5)----[f10]-c4
              |-[f1]----------------(cbl1)----------------[f2]-|

     */
    public static Substation build() {
        return build(
                new boolean[]{true, true,true,true,true,true,true,true,true,true},
                new double[5]
                );
    }

    public static Substation build(boolean[] fuseClose, double[] consumptions) {
        var subs = new Substation("subs");
        var cab1 = new Cabinet("cab1");
        var cab2 = new Cabinet("cab2");
        var cab3 = new Cabinet("cab3");
        var cab4 = new Cabinet("cab4");

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
        var cbl5 = new Cable();
        var m5 = new Meter("m5");
        m5.setConsumption(consumptions[4]);
        cbl5.addMeters(m5);

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
        var f9 = new Fuse(F9_NAME);
        if(!fuseClose[8]) {
            f9.openFuse();
        }
        var f10 = new Fuse(F10_NAME);
        if(!fuseClose[9]) {
            f10.openFuse();
        }

        subs.addFuses(f1, f3);
        cab1.addFuses(f4, f5, f7);
        cab2.addFuses(f2, f8, f9);
        cab3.addFuses(f6);
        cab4.addFuses(f10);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);

        return subs;
    }

    public static Fuse[] extractFuses(Substation substation) {
        var fuses = new Fuse[10];
        fuses[0] = substation.getFuses().get(0);
        fuses[1] = fuses[0].getOpposite();
        fuses[2] = substation.getFuses().get(1);
        fuses[3] = fuses[2].getOpposite();

        var cab1 = fuses[3].getOwner();
        fuses[4] = cab1.getFuses().get(1);
        fuses[5] = fuses[4].getOpposite();
        fuses[6] = cab1.getFuses().get(2);
        fuses[7] = fuses[6].getOpposite();

        var cab2 = fuses[7].getOwner();
        fuses[8] = cab2.getFuses().get(2);
        fuses[9] = fuses[8].getOpposite();

        return fuses;
    }

    public static Cable[] extractCables(Substation substation) {
        var cables = new Cable[5];

        var fuses = extractFuses(substation);
        cables[0] = fuses[0].getCable();
        cables[1] = fuses[2].getCable();
        cables[2] = fuses[4].getCable();
        cables[3] = fuses[6].getCable();
        cables[4] = fuses[8].getCable();


        return cables;
    }
}
