package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Entity;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OppositeTest {
    @Test
    public void testOpposite() {
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

        assertEquals(f1.getOpposite(), e2);
        assertEquals(f2.getOpposite(), e1);
    }
}
