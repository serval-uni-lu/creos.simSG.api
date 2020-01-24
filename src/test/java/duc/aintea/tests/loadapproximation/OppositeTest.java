package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Entity;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OppositeTest {
    @Test
    public void allClosed() {
        Cable c1 = new Cable();

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        c1.setFirstFuse(f1);
        c1.setSecondFuse(f2);

        Entity e1 = new Substation("subs");
        Entity e2 = new Substation("subs2");
        e1.addFuses(f1);
        e2.addFuses(f2);

        assertEquals(f1.getOwner(), e1);
        assertEquals(f2.getOwner(), e2);

        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertTrue(oppositeF1.isPresent());
        assertTrue(oppositeF2.isPresent());

        assertEquals(oppositeF1.get(), e2);
        assertEquals(oppositeF2.get(), e1);
    }

    @Test
    public void allOpen() {
        Cable c1 = new Cable();

        Fuse f1 = new Fuse("f1");
        f1.openFuse();
        Fuse f2 = new Fuse("f2");
        f2.openFuse();
        c1.setFirstFuse(f1);
        c1.setSecondFuse(f2);

        Entity e1 = new Substation("subs");
        Entity e2 = new Substation("subs2");
        e1.addFuses(f1);
        e2.addFuses(f2);

        assertEquals(f1.getOwner(), e1);
        assertEquals(f2.getOwner(), e2);

        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertTrue(oppositeF1.isEmpty());
        assertTrue(oppositeF2.isEmpty());
    }

    @Test
    public void firstOpen() {
        Cable c1 = new Cable();

        Fuse f1 = new Fuse("f1");
        f1.openFuse();
        Fuse f2 = new Fuse("f2");
        c1.setFirstFuse(f1);
        c1.setSecondFuse(f2);

        Entity e1 = new Substation("subs");
        Entity e2 = new Substation("subs2");
        e1.addFuses(f1);
        e2.addFuses(f2);

        assertEquals(f1.getOwner(), e1);
        assertEquals(f2.getOwner(), e2);

        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertTrue(oppositeF1.isEmpty());
        assertTrue(oppositeF2.isEmpty());
    }

    @Test
    public void secondOpen() {
        Cable c1 = new Cable();

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        f2.openFuse();
        c1.setFirstFuse(f1);
        c1.setSecondFuse(f2);

        Entity e1 = new Substation("subs");
        Entity e2 = new Substation("subs2");
        e1.addFuses(f1);
        e2.addFuses(f2);

        assertEquals(f1.getOwner(), e1);
        assertEquals(f2.getOwner(), e2);

        Optional<Entity> oppositeF1 = f1.getOpposite();
        Optional<Entity> oppositeF2 = f2.getOpposite();

        assertTrue(oppositeF1.isEmpty());
        assertTrue(oppositeF2.isEmpty());
    }
}
