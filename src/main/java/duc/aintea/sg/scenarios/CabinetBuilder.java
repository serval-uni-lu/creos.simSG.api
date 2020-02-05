package duc.aintea.sg.scenarios;

import duc.aintea.sg.Cabinet;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;

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
        var subs = new Substation("substation");
        var cab1 = new Cabinet("cabinet1");
        var cab2 = new Cabinet("cabinet1");
        var cab3 = new Cabinet("cabinet1");

        var f1 = new Fuse(F1_NAME);
        var f2 = new Fuse(F2_NAME);
        var f3 = new Fuse(F3_NAME);
        var f4 = new Fuse(F4_NAME);
        var f5 = new Fuse(F5_NAME);
        var f6 = new Fuse(F6_NAME);

        var cbl1 = new Cable();
        var cbl2 = new Cable();
        var cbl3 = new Cable();

        subs.addFuses(f1);
        cab1.addFuses(f2, f3, f4);
        cab2.addFuses(f5);
        cab3.addFuses(f6);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f5);
        cbl3.setFuses(f4, f6);

        return subs;
    }
}
