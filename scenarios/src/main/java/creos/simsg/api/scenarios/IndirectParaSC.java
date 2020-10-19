package creos.simsg.api.scenarios;

import creos.simsg.api.model.Cable;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.SmartGrid;

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
    public static final String F1_NAME = "i1";
    public static final String F2_NAME = "i2";
    public static final String F3_NAME = "i3";
    public static final String F4_NAME = "i4";
    public static final String F5_NAME = "i5";
    public static final String F6_NAME = "i6";
    public static final String F7_NAME = "i7";
    public static final String F8_NAME = "i8";
    public static final String F9_NAME = "i9";
    public static final String F10_NAME = "i10";


    public static final String SUBSTATION_NAME = "Substation";
    public static final String CABINET1_NAME = "Cabinet 1";
    public static final String CABINET2_NAME = "Cabinet 2";
    public static final String CABINET3_NAME = "Cabinet 3";
    public static final String CABINET4_NAME = "Cabinet 4";

    IndirectParaSC(SmartGrid grid) {
        super(grid);
    }

    @Override
    public String substationName() {
        return SUBSTATION_NAME;
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
