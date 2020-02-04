package duc.aintea.sg.scenarios;

import duc.aintea.sg.Cabinet;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;

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
        var subs = new Substation("substation");
        var c1 = new Cabinet("c1");
        var c2 = new Cabinet("c2");
        var c3 = new Cabinet("c3");

        var cbl1 = new Cable();
        var cbl2 = new Cable();
        var cbl3 = new Cable();
        var cbl4 = new Cable();

        var f1 = new Fuse(F1_NAME);
        var f2 = new Fuse(F2_NAME);
        var f3 = new Fuse(F3_NAME);
        var f4 = new Fuse(F4_NAME);
        var f5 = new Fuse(F5_NAME);
        var f6 = new Fuse(F6_NAME);
        var f7 = new Fuse(F7_NAME);
        var f8 = new Fuse(F8_NAME);

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
