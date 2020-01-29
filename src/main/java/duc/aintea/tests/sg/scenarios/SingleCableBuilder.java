package duc.aintea.tests.sg.scenarios;

import duc.aintea.tests.sg.Cabinet;
import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;

public class SingleCableBuilder {

    public static Substation build(double consumption) {
        var substation = new Substation("substation");
        var cabinet = new Cabinet("cabinet");

        var f1 = new Fuse("fuse_subs");
        var f2 = new Fuse("fuse_cabinet");

        var cable = new Cable();

        substation.addFuses(f1);
        cabinet.addFuses(f2);

        cable.setFuses(f1, f2);

        return substation;
    }

}
