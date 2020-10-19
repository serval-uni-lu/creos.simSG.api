package creos.simsg.api.scenarios;

import creos.simsg.api.model.*;
import creos.simsg.api.model.*;

public class LinkedSubstationSC extends Scenario {
    public static final String F1_NAME = "i1";
    public static final String F2_NAME = "i2";
    public static final String F3_NAME = "i3";
    public static final String F4_NAME = "i4";
    public static final String F5_NAME = "i5";
    public static final String F6_NAME = "i6";
    public static final String SUBSTATION_NAME_1 = "Substation_1";
    public static final String CABINET1_NAME = "Cabinet 1";
    public static final String SUBSTATION_NAME_2 = "Substation_2";
    private Cable[] cables;
    private Fuse[] fuses;

    //TODO enhance scenario creation , how to include the Cabinet ?
    LinkedSubstationSC(SmartGrid grid) {
        super(grid);
        Substation ss1 = new Substation(SUBSTATION_NAME_1);
        this.grid.addSubstations(ss1);
        Substation ss2 = new Substation(SUBSTATION_NAME_2);
        Cabinet cabinet = new Cabinet(CABINET1_NAME);
        grid.addSubstations(ss1);
        grid.addSubstations(ss2);
        //Fuse creation
        Fuse f1,f2,f3,f4,f5,f6;
//        f1 = new Fuse(F1_NAME);
        f2 = new Fuse(F2_NAME);
        f3 = new Fuse(F3_NAME);
        f4 = new Fuse(F4_NAME);
        f5 = new Fuse(F5_NAME);
//        f6 = new Fuse(F6_NAME);

        //Fuses assignment
//        f1.setOwner(ss1);
//        f1.openFuse();
        f2.setOwner(ss1);
        f2.closeFuse();
        f3.setOwner(cabinet);
        f3.closeFuse();
        f4.setOwner(cabinet);
        f4.closeFuse();
        f5.setOwner(ss2);
        f5.closeFuse();
//        f6.setOwner(ss2);
//        f6.openFuse();

//        fuses = new Fuse[]{f1, f2, f3, f4, f5, f6};
        fuses = new Fuse[]{f2, f3, f4, f5};
        //Cable creation
        Cable c1 = new Cable("c1");
        Cable c2 = new Cable("c2");

        cables = new Cable[]{c1,c2};

        //Cable assignment
        c1.setFirstFuse(f2);
        c1.setSecondFuse(f3);
        c2.setFirstFuse(f4);
        c2.setSecondFuse(f5);

        ss1.addFuses(f2);
        cabinet.addFuses(f3,f4);
        ss2.addFuses(f5);
    }

    @Override
    public String substationName() {
        return SUBSTATION_NAME_1;
    }

    @Override
    public Fuse[] extractFuses() {
        return fuses;
    }

    @Override
    public Cable[] extractCables() {
        return cables;
    }
}
