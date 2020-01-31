package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OppositeTest {
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
        Fuse oppositeF1 = f1.getOpposite();
        Fuse oppositeF2 = f2.getOpposite();

        List<Entity> oppReachSubs1 = subs1.getReachableNeighbors();
        List<Entity> oppReachC2 = c2.getReachableNeighbors();

        assertEquals(f2, oppositeF1);
        assertEquals(f1, oppositeF2);

        assertEquals(1, oppReachSubs1.size());
        assertEquals(c2, oppReachSubs1.get(0));

        assertEquals(1, oppReachC2.size());
        assertEquals(subs1, oppReachC2.get(0));
    }

    /*
        Subs1--]f1[----(cbl1)----]f2[--c2
    */
    @Test
    public void testAllOpen() {
        f1.openFuse();
        f2.openFuse();

        Fuse oppositeF1 = f1.getOpposite();
        Fuse oppositeF2 = f2.getOpposite();

        List<Entity> oppReachSubs1 = subs1.getReachableNeighbors();
        List<Entity> oppReachC2 = c2.getReachableNeighbors();

        assertEquals(f2, oppositeF1);
        assertEquals(f1, oppositeF2);

        assertEquals(0, oppReachSubs1.size());
        assertEquals(0, oppReachC2.size());
    }


    /*
        Subs1--]f1[----(cbl1)----[f2]--c2
    */
    @Test
    public void testF1Open() {
        f1.openFuse();

        Fuse oppositeF1 = f1.getOpposite();
        Fuse oppositeF2 = f2.getOpposite();

        List<Entity> oppReachSubs1 = subs1.getReachableNeighbors();
        List<Entity> oppReachC2 = c2.getReachableNeighbors();

        assertEquals(f2, oppositeF1);
        assertEquals(f1, oppositeF2);

        assertEquals(0, oppReachSubs1.size());
        assertEquals(0, oppReachC2.size());
    }

    /*
            Subs1--[f1]----(cbl1)----]f2[--c2
     */
    @Test
    public void testF2Open() {
        f2.openFuse();

        Fuse oppositeF1 = f1.getOpposite();
        Fuse oppositeF2 = f2.getOpposite();

        List<Entity> oppReachSubs1 = subs1.getReachableNeighbors();
        List<Entity> oppReachC2 = c2.getReachableNeighbors();

        assertEquals(f2, oppositeF1);
        assertEquals(f1, oppositeF2);

        assertEquals(0, oppReachSubs1.size());
        assertEquals(0, oppReachC2.size());
    }
}
