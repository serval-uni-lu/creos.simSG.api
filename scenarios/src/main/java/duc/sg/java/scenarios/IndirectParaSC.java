package duc.sg.java.scenarios;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;

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
public final class IndirectParaSC extends Scenario {
    public static final String F1_NAME = "fuse1_subs";
    public static final String F2_NAME = "fuse1_cab_2";
    public static final String F3_NAME = "fuse2_subs";
    public static final String F4_NAME = "fuse1_cab_1";
    public static final String F5_NAME = "fuse2_cab_1";
    public static final String F6_NAME = "fuse_cab_3";
    public static final String F7_NAME = "fuse3_cab_1";
    public static final String F8_NAME = "fuse2_cab_2";
    public static final String F9_NAME = "fuse3_cab_2";
    public static final String F10_NAME = "fuse_cab_4";


    public static final String SUBSTATION_NAME = "SUBSTATION";
    public static final String CABINET1_NAME = "Cabinet 1";
    public static final String CABINET2_NAME = "Cabinet 2";
    public static final String CABINET3_NAME = "Cabinet 3";
    public static final String CABINET4_NAME = "Cabinet 4";

    IndirectParaSC(SmartGrid grid) {
        super(grid);
    }


    @Override
    public Fuse[] extractFuses() {
        var substation = grid.getSubstation(SUBSTATION_NAME).get();

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

    @Override
    public Cable[] extractCables() {
        var cables = new Cable[5];

        var fuses = extractFuses();
        cables[0] = fuses[0].getCable();
        cables[1] = fuses[2].getCable();
        cables[2] = fuses[4].getCable();
        cables[3] = fuses[6].getCable();
        cables[4] = fuses[8].getCable();

        return cables;
    }
}
