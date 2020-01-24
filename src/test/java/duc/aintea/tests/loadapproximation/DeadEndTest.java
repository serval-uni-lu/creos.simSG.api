package duc.aintea.tests.loadapproximation;

import duc.aintea.tests.sg.Cabinet;
import duc.aintea.tests.sg.Cable;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeadEndTest {

    @Test
    public void testOneFuseCabinet() {
        Cabinet c1 = new Cabinet("c1");
        Fuse f1 = new Fuse("f1");
        c1.addFuses(f1);
        assertTrue(c1.isDeadEnd());
    }

    @Test
    public void testParallelCablesDE() {
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();

        c1.addFuses(f1, f3);
        c2.addFuses(f2, f4);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);

        assertTrue(c1.isDeadEnd());
        assertTrue(c2.isDeadEnd());

    }

    @Test
    public void testParallelCables() {
        Cabinet c1 = new Cabinet("c1");
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

        c1.addFuses(f1, f3);
        c2.addFuses(f2, f4, f6);
        c3.addFuses(f5);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);

        assertTrue(c1.isDeadEnd());
        assertFalse(c2.isDeadEnd());
        assertTrue(c3.isDeadEnd());
    }

    @Test
    public void testParallelCablesSub() {
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Substation c3 = new Substation("c3");

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
        c3.addFuses(f5);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);

        assertTrue(c1.isDeadEnd());
        assertFalse(c2.isDeadEnd());
        assertFalse(c3.isDeadEnd());
    }

    @Test
    public void testOneFuseSubs() {
        Cabinet c1 = new Cabinet("c1");
        Fuse f1 = new Fuse("f1");
        c1.addFuses(f1);
        assertTrue(c1.isDeadEnd());
    }

    @Test
    public void testOneCable() {
        Substation s1 = new Substation("s1");
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();

        s1.addFuses(f1);
        c1.addFuses(f2, f3);
        c2.addFuses(f4);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);

        assertFalse(s1.isDeadEnd());
        assertFalse(c1.isDeadEnd());
        assertTrue(c2.isDeadEnd());
    }

}
