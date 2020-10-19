package creos.simsg.api.scenarios;

import creos.simsg.api.model.Cable;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.SmartGrid;

/*
                                    |-[f3]----(cbl2)----[f4]-|
    subs-[f1]----(cbl1)----[f2]-c1-<                          >-c2-[f7]----(cbl4)---[f8]-c3
                                    |-[f5]----(cbl3)----[f6]-|
 */
public final class ParaCabinetSC extends Scenario {
    public static final  String F1_NAME = "i1";
    public static final  String F2_NAME = "i2";
    public static final  String F3_NAME = "i3";
    public static final  String F4_NAME = "i4";
    public static final  String F5_NAME = "i5";
    public static final  String F6_NAME = "i6";
    public static final  String F7_NAME = "i7";
    public static final  String F8_NAME = "i8";

    public static final String SUBSTATION_NAME = "Substation";
    public static final String CABINET1_NAME = "Cabinet 1";
    public static final String CABINET2_NAME = "Cabinet 2";
    public static final String CABINET3_NAME = "Cabinet 3";

    ParaCabinetSC(SmartGrid grid) {
        super(grid);
    }

    @Override
    public String substationName() {
        return SUBSTATION_NAME;
    }

    @Override
    public Fuse[] extractFuses() {
        var substation = grid.getSubstation(SUBSTATION_NAME).get();

        var res = new Fuse[8];
        res[0] = substation.getFuses().get(0);
        res[1] = res[0].getOpposite();

        var cab1 = res[1].getOwner();
        res[2] = cab1.getFuses().get(1);
        res[3] = res[2].getOpposite();
        res[4] = cab1.getFuses().get(2);
        res[5] = res[4].getOpposite();

        var cab2 = res[3].getOwner();
        res[6] = cab2.getFuses().get(2);
        res[7] = res[6].getOpposite();

        return res;
    }

    @Override
    public Cable[] extractCables() {
        var cables = new Cable[4];
        var fuses = extractFuses();

        cables[0] = fuses[0].getCable();
        cables[1] = fuses[2].getCable();
        cables[2] = fuses[4].getCable();
        cables[3] = fuses[6].getCable();

        return cables;
    }
}
