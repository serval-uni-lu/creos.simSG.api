package duc.aintea.loadapproximation.test;

import duc.aintea.sg.Fuse;
import duc.aintea.sg.Cabinet;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Substation;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeadEndTest {

    /*
               |-[f1]----(cbl1)----[f2]-|
        subs1-<                         >-c2
               |-[f3]----(cbl2)----[f4]-|
     */
    @Test
    public void testParallelCablesDE() {
        Substation subs1 = new Substation("c1");
        Cabinet c2 = new Cabinet("c2");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();

        subs1.addFuses(f1, f3);
        c2.addFuses(f2, f4);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);

        assertFalse(subs1.isDeadEnd());
        assertFalse(c2.isDeadEnd());
    }

    /*
               |-[f1]----(cbl1)----[f2]-|
        subs1-<                          >-c2-[f6]----(cbl3)----[f5]-c3
               |-[f3]----(cbl1)----[f4]-|
     */
    @Test
    public void testParallelCables() {
        Substation subs1 = new Substation("subs1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();

        subs1.addFuses(f1, f3);
        c2.addFuses(f2, f4, f6);
        c3.addFuses(f5);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);

        assertFalse(subs1.isDeadEnd());
        assertFalse(c2.isDeadEnd());
        assertTrue(c3.isDeadEnd());
    }

    /*
                                         |-[f2]----(cbl1)----[f1]-|
        subs1-[f5]----(cbl3)----[f6]-c2-<                          >-c1
                                         |-[f4]----(cbl2)----[f3]-|
     */
    @Test
    public void testParallelCablesSub() {
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Substation subs1 = new Substation("subs1");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();

        c1.addFuses(f1, f3);
        c2.addFuses(f2, f4, f6);
        subs1.addFuses(f5);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);

        assertFalse(c1.isDeadEnd());
        assertFalse(c2.isDeadEnd());
        assertFalse(subs1.isDeadEnd());
    }

    /*
        subs1-[f1]----(cbl1)----[f2]-c1-[f3]----(cbl2)----[f4]-(c2)
     */
    @Test
    public void testOneCable() {
        Substation subs1 = new Substation("subs1");
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();

        subs1.addFuses(f1);
        c1.addFuses(f2, f3);
        c2.addFuses(f4);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);

        assertFalse(subs1.isDeadEnd());
        assertFalse(c1.isDeadEnd());
        assertTrue(c2.isDeadEnd());
    }

    /*
                                          |-[f3]----(cbl2)----]f5[-cab2
        subs-[f1]----(cbl1)----[f2]-cab1-<
                                          |-[f4]----(cbl3)----]f6[-cab3
     */
    @Test
    public void cabinetScenario() {
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

        f5.openFuse();
        f6.openFuse();

        assertFalse(subs.isDeadEnd());
        assertFalse(cab1.isDeadEnd());
        assertTrue(cab2.isDeadEnd());
        assertTrue(cab3.isDeadEnd());

    }



    /*
               |-[f1]----(cbl1)----[f2]-|
        subs1-<                          >-c2-]f6[----(cbl3)----[f5]-c3
               |-]f3[----(cbl1)----[f4]-|
     */
    @Test
    public void testParallelCablesOpenFuses() {
        Substation subs1 = new Substation("subs1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();

        subs1.addFuses(f1, f3);
        c2.addFuses(f2, f4, f6);
        c3.addFuses(f5);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);


        f3.openFuse();
        f6.openFuse();

        assertFalse(subs1.isDeadEnd());
        assertFalse(c2.isDeadEnd());
        assertTrue(c3.isDeadEnd());
    }

}
