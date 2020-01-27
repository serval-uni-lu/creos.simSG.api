package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Entity;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OppositeTest {
    private Entity subs1, subs2;
    private Fuse f1, f2;

    /*
           Subs1--[f1]----(cbl1)----[f2]--Subs2
    */
    @BeforeEach
    public void init() {
        Cable cbl1 = new Cable();

        f1 = new Fuse("f1");
        f2 = new Fuse("f2");
        cbl1.setFirstFuse(f1);
        cbl1.setSecondFuse(f2);

        subs1 = new Substation("subs");
        subs2 = new Substation("subs2");
        subs1.addFuses(f1);
        subs2.addFuses(f2);
    }



    @Test
    public void allClosed() {
        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertEquals(f1.getOwner(), subs1);
        assertEquals(f2.getOwner(), subs2);

        assertTrue(oppositeF1.isPresent());
        assertTrue(oppositeF2.isPresent());

        assertEquals(oppositeF1.get(), subs2);
        assertEquals(oppositeF2.get(), subs1);
    }

    @Test
    public void allOpen() {
        f1.openFuse();
        f2.openFuse();
        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertEquals(f1.getOwner(), subs1);
        assertEquals(f2.getOwner(), subs2);

        assertTrue(oppositeF1.isEmpty());
        assertTrue(oppositeF2.isEmpty());
    }


    @Test
    public void firstOpen() {
        f1.openFuse();
        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertEquals(f1.getOwner(), subs1);
        assertEquals(f2.getOwner(), subs2);

        assertTrue(oppositeF1.isEmpty());
        assertTrue(oppositeF2.isEmpty());
    }

    /*
            Subs1--[f1]----(cbl1)----]f2[--Subs2
     */
    @Test
    public void secondOpen() {
        f2.openFuse();
        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertEquals(f1.getOwner(), subs1);
        assertEquals(f2.getOwner(), subs2);

        assertTrue(oppositeF1.isEmpty());
        assertTrue(oppositeF2.isEmpty());
    }
}
