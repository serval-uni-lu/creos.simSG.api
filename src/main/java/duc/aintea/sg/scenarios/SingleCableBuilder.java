package duc.aintea.sg.scenarios;

import duc.aintea.sg.Cabinet;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;

public class SingleCableBuilder {
    public static final String F1_NAME = "fuse_subs";
    public static final String F2_NAME = "fuse_cabinet";

    /*
                subs-[f1]----(cbl1)----[f2]-cab
             */
    public static Substation build() {
        var subs = new Substation("substation");
        var cab = new Cabinet("cabinet");

        var f1 = new Fuse(F1_NAME);
        var f2 = new Fuse(F2_NAME);

        var cable = new Cable();

        subs.addFuses(f1);
        cab.addFuses(f2);

        cable.setFuses(f1, f2);

        return subs;
    }

}
