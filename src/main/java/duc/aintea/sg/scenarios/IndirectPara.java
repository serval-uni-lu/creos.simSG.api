package duc.aintea.sg.scenarios;

import duc.aintea.sg.Cabinet;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;

public class IndirectPara {
    public static final String F1_NAME = "fuse1_subs";
    public static final String F3_NAME = "fuse2_subs";

    public static final String F4_NAME = "fuse1_cab_1";
    public static final String F5_NAME = "fuse2_cab_1";
    public static final String F7_NAME = "fuse3_cab_1";


    public static final String F2_NAME = "fuse1_cab_2";
    public static final String F8_NAME = "fuse2_cab_2";
    public static final String F9_NAME = "fuse3_cab_2";


    public static final String F6_NAME = "fuse_cab_3";

    public static final String F10_NAME = "fuse_cab_4";







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
    public static Substation build() {
        var subs = new Substation("subs");
        var cab1 = new Cabinet("cab1");
        var cab2 = new Cabinet("cab2");
        var cab3 = new Cabinet("cab3");
        var cab4 = new Cabinet("cab4");

        var cbl1 = new Cable();
        var cbl2 = new Cable();
        var cbl3 = new Cable();
        var cbl4 = new Cable();
        var cbl5 = new Cable();

        var f1 = new Fuse(F1_NAME);
        var f2 = new Fuse(F2_NAME);
        var f3 = new Fuse(F3_NAME);
        var f4 = new Fuse(F4_NAME);
        var f5 = new Fuse(F5_NAME);
        var f6 = new Fuse(F6_NAME);
        var f7 = new Fuse(F7_NAME);
        var f8 = new Fuse(F8_NAME);
        var f9 = new Fuse(F9_NAME);
        var f10 = new Fuse(F10_NAME);

        subs.addFuses(f1, f3);
        cab1.addFuses(f4, f5, f7);
        cab2.addFuses(f2, f8, f9);
        cab3.addFuses(f6);
        cab4.addFuses(f10);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);

        return subs;
    }
}
