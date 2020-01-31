package duc.aintea.tests.sg.scenarios;

import duc.aintea.tests.sg.Cabinet;
import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;

public class ParaTransformaterBuilder {

    /*
              |-[f1]----(cbl1)----[f2]-|
        subs-<                          >-c1-[f5]----(cbl3)---[f6]-c2
              |-[f3]----(cbl2)----[f4]-|
     */
    public static Substation build() {
        var subs = new Substation("substation");
        var c1 = new Cabinet("cabinet1");
        var c2 = new Cabinet("cabinet2");

        var f1 = new Fuse("fuse1_subs");
        var f2 = new Fuse("fuse1_cabinet_1");
        var f3 = new Fuse("fuse2_subs");
        var f4 = new Fuse("fuse2_cabinet_1");
        var f5 = new Fuse("fuse3_cabinet_1");
        var f6 = new Fuse("fuse_cabinet2");

        var cbl1 = new Cable();
        var cbl2 = new Cable();
        var cbl3 = new Cable();

        subs.addFuses(f1, f3);
        c1.addFuses(f2, f4, f5);
        c2.addFuses(f6);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);

        return subs;
    }


}
