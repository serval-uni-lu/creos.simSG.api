package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.*;

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
        assertTrue(c2.isDeadEnd());

        Optional<Entity> oppDEF1 = f1.getOppDeadEnds();
        Optional<Entity> oppDEF2 = f2.getOppDeadEnds();
        Optional<Entity> oppDEF3 = f3.getOppDeadEnds();
        Optional<Entity> oppDEF4 = f4.getOppDeadEnds();

        assertTrue(oppDEF1.isPresent());
        assertTrue(oppDEF2.isEmpty());
        assertTrue(oppDEF3.isPresent());
        assertTrue(oppDEF4.isEmpty());

        assertEquals(c2, oppDEF1.get());
        assertEquals(c2, oppDEF3.get());
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

        assertTrue(c1.isDeadEnd());
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

}
