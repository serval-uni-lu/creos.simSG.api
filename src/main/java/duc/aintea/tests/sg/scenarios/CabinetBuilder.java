package duc.aintea.tests.sg.scenarios;

import duc.aintea.tests.sg.Cabinet;
import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;

public class CabinetBuilder {

    /*
                                          |-[f3]----(cbl2)----[f5]-cab2
        subs-[f1]----(cbl1)----[f2]-cab1-<
                                          |-[f4]----(cbl3)----[f6]-cab3
     */
    public static Substation build(double consumption) {
        var subs = new Substation("substation");
        var cab1 = new Cabinet("cabinet1");
        var cab2 = new Cabinet("cabinet1");
        var cab3 = new Cabinet("cabinet1");

        var f1 = new Fuse("fuse_subs");
        var f2 = new Fuse("fuse1_cabinet_1");
        var f3 = new Fuse("fuse2_cabinet_1");
        var f4 = new Fuse("fuse3_cabinet_1");
        var f5 = new Fuse("fuse_cabinet2");
        var f6 = new Fuse("fuse_cabinet3");

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
