package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OppDeadEndTests {

    private Entity subs1, c2;
    private Fuse f1, f2;

    /*
        Subs1--[f1]----(cbl1)----[f2]--c2
    */
    @BeforeEach
    public void init() {
        Cable cbl1 = new Cable();

        f1 = new Fuse("f1");
        f2 = new Fuse("f2");
        cbl1.setFirstFuse(f1);
        cbl1.setSecondFuse(f2);

        subs1 = new Substation("subs");
        c2 = new Cabinet("c2");
        subs1.addFuses(f1);
        c2.addFuses(f2);
    }

    /*
        Subs1--[f1]----(cbl1)----[f2]--c2
    */
    @Test
    public void testAllClosed() {
//        List<Entity> oppDESubs1 = subs1.getDeadEndsNeighbors();
//        List<Entity> oppDEC2 = c2.getDeadEndsNeighbors();
//
//        assertEquals(1, oppDESubs1.size());
//        assertEquals(c2, oppDESubs1.get(0));
//        assertEquals(0, oppDEC2.size());
    }

    /*
       Subs1--]f1[----(cbl1)----]f2[--c2
   */
    @Test
    public void testAllOpen() {
//        f1.openFuse();
//        f2.openFuse();
//
//        List<Entity> oppDESubs1 = subs1.getDeadEndsNeighbors();
//        List<Entity> oppDEC2 = c2.getDeadEndsNeighbors();
//
//        assertEquals(0, oppDESubs1.size());
//        assertEquals(0, oppDEC2.size());
    }

    /*
        Subs1--[f1]----(cbl1)----]f2[--c2
   */
    @Test
    public void testF2Open() {
//        f2.openFuse();
//
//        List<Entity> oppDESubs1 = subs1.getDeadEndsNeighbors();
//        List<Entity> oppDEC2 = c2.getDeadEndsNeighbors();
//
//        assertEquals(1, oppDESubs1.size());
//        assertEquals(c2, oppDESubs1.get(0));
//        assertEquals(0, oppDEC2.size());
    }
}
