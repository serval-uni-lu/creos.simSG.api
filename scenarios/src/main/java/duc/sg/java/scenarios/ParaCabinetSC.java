package duc.sg.java.scenarios;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;

/*
                                    |-[f3]----(cbl2)----[f4]-|
    subs-[f1]----(cbl1)----[f2]-c1-<                          >-c2-[f7]----(cbl4)---[f8]-c3
                                    |-[f5]----(cbl3)----[f6]-|
 */
public final class ParaCabinetSC extends Scenario {
    public static final  String F1_NAME = "fuse_subs";
    public static final  String F2_NAME = "fuse1_cabinet_1";
    public static final  String F3_NAME = "fuse2_cabinet_1";
    public static final  String F4_NAME = "fuse1_cabinet_2";
    public static final  String F5_NAME = "fuse3_cabinet_1";
    public static final  String F6_NAME = "fus2_cabinet_2";
    public static final  String F7_NAME = "fus3_cabinet_2";
    public static final  String F8_NAME = "fuse_cabinet_3";

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
