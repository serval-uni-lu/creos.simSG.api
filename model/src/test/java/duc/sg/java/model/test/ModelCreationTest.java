package duc.sg.java.model.test;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Entity;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelCreationTest {

    @Test
    public void testOwner() {
        Entity e1 = new Substation("subs");
        Fuse f1 = new Fuse("f1");
        e1.addFuses(f1);

        assertEquals(e1, f1.getOwner());
    }

    @Test
    public void testCableFuse() {
        Cable c1 = new Cable();
        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");

        c1.setFirstFuse(f1);
        c1.setSecondFuse(f2);

        assertEquals(c1, f1.getCable());
        assertEquals(c1, f2.getCable());

    }


}
