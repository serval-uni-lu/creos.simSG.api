package duc.sg.java.scenarios;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;

/*
          |-[f1]----(cbl1)----[f2]-|
    subs-<                          >-c1-[f5]----(cbl3)---[f6]-c2
          |-[f3]----(cbl2)----[f4]-|
*/
public final class ParaTransformerSC extends Scenario {
    public static final  String F1_NAME = "i1";
    public static final  String F2_NAME = "i2";
    public static final  String F3_NAME = "i3";
    public static final  String F4_NAME = "i4";
    public static final  String F5_NAME = "i5";
    public static final  String F6_NAME = "i6";

    public static final  String SUBSTATION_NAME = "Substation";
    public static final  String CABINET1_NAME = "Cabinet 1";
    public static final  String CABINET2_NAME = "Cabinet 2";


    ParaTransformerSC(SmartGrid grid) {
        super(grid);
    }

    @Override
    public String substationName() {
        return SUBSTATION_NAME;
    }

    @Override
    public Fuse[] extractFuses() {
        var substation = grid.getSubstation(SUBSTATION_NAME).get();

        var res = new Fuse[6];
        res[0] = substation.getFuses().get(0);
        res[1] = res[0].getOpposite();
        res[2] = substation.getFuses().get(1);
        res[3] = res[2].getOpposite();

        var cab = res[1].getOwner();
        res[4] = cab.getFuses().get(2);
        res[5] = res[4].getOpposite();

        return res;
    }

    @Override
    public Cable[] extractCables() {
        var fuses = extractFuses();

        var res = new Cable[3];
        res[0] = fuses[0].getCable();
        res[1] = fuses[2].getCable();
        res[2] = fuses[4].getCable();

        return res;
    }
}
