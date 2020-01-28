package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Cabinet;
import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
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
        assertTrue(c2.isDeadEnd());

//        List<Entity> oppDESubs1 = subs1.getDeadEndsNeighbors();
//        List<Entity> oppDEC2 = c2.getDeadEndsNeighbors();
//
//        assertEquals(2, oppDESubs1.size());
//        assertEquals(c2, oppDESubs1.get(0));
//        assertEquals(c2, oppDESubs1.get(1));
//        assertEquals(0, oppDEC2.size());
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

//        List<Entity> oppDESubs1 = subs1.getDeadEndsNeighbors();
//        List<Entity> oppDEC2 = c2.getDeadEndsNeighbors();
//        List<Entity> oppDEC3 = c3.getDeadEndsNeighbors();
//
//        assertEquals(0, oppDESubs1.size());
//        assertEquals(1, oppDEC2.size());
//        assertEquals(c3, oppDEC2.get(0));
//        assertEquals(0, oppDEC3.size());
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

//        List<Entity> oppDESubs1 = subs1.getDeadEndsNeighbors();
//        List<Entity> oppDEC1 = c1.getDeadEndsNeighbors();
//        List<Entity> oppDEC2 = c2.getDeadEndsNeighbors();
//
//        assertEquals(0, oppDESubs1.size());
//        assertEquals(0, oppDEC1.size());
//        assertEquals(2, oppDEC2.size());
//        assertEquals(c1, oppDEC2.get(0));
//        assertEquals(c1, oppDEC2.get(1));
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

//        List<Entity> oppDESubs1 = subs1.getDeadEndsNeighbors();
//        List<Entity> oppDEC1 = c1.getDeadEndsNeighbors();
//        List<Entity> oppDEC2 = c2.getDeadEndsNeighbors();
//
//        assertEquals(0, oppDESubs1.size());
//        assertEquals(1, oppDEC1.size());
//        assertEquals(c2, oppDEC1.get(0));
//        assertEquals(0, oppDEC2.size());

    }

}
