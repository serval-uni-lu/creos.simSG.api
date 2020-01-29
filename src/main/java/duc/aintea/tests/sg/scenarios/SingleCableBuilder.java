package duc.aintea.tests.sg.scenarios;

import duc.aintea.tests.sg.Cabinet;
import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;

public class SingleCableBuilder {

    /*
        subs-[f1]----(cbl1)----[f2]-cab
     */
    public static Substation build(double consumption) {
        var subs = new Substation("substation");
        var cab = new Cabinet("cabinet");

        var f1 = new Fuse("fuse_subs");
        var f2 = new Fuse("fuse_cabinet");

        var cable = new Cable();

        subs.addFuses(f1);
        cab.addFuses(f2);

        cable.setFuses(f1, f2);

        return subs;
    }

}