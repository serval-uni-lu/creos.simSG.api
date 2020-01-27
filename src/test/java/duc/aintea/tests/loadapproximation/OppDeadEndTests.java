package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        Optional<Entity> oppDEF1 = f1.getOppDeadEnds();
        Optional<Entity> oppDEF2 = f2.getOppDeadEnds();

        assertTrue(oppDEF1.isPresent());
        assertTrue(oppDEF2.isEmpty());
        assertEquals(oppDEF1.get(), c2);
    }

    /*
       Subs1--]f1[----(cbl1)----]f2[--c2
   */
    @Test
    public void testAllOpen() {
        f1.openFuse();
        f2.openFuse();

        Optional<Entity> oppDEF1 = f1.getOppDeadEnds();
        Optional<Entity> oppDEF2 = f2.getOppDeadEnds();

        assertTrue(oppDEF1.isEmpty());
        assertTrue(oppDEF2.isEmpty());
    }

    /*
        Subs1--[f1]----(cbl1)----]f2[--c2
   */
    @Test
    public void testF2Open() {
        f2.openFuse();

        Optional<Entity> oppDEF1 = f1.getOppDeadEnds();
        Optional<Entity> oppDEF2 = f2.getOppDeadEnds();

        assertTrue(oppDEF1.isPresent());
        assertTrue(oppDEF2.isEmpty());
        assertEquals(oppDEF1.get(), c2);
    }
}
