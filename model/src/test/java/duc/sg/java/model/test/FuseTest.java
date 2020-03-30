package duc.sg.java.model.test;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.uncertainty.Confidence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FuseTest {


    @Test
    public void testFuse() {
        var f1 = new Fuse("f1");
        var f2 = new Fuse("f2");
        var cable = new Cable();
        cable.setFuses(f1, f2);

        assertEquals(f1, f2.getOpposite());
        assertEquals(f2, f1.getOpposite());

        assertEquals(cable, f1.getCable());
        assertEquals(cable, f2.getCable());

        assertTrue(f1.isClosed());
        assertTrue(f1.getStatus().isCertain());
        assertEquals(Confidence.MAX_PROBABILITY, f1.getStatus().confIsClosed());
        assertTrue(f2.isClosed());
        assertTrue(f2.getStatus().isCertain());

        f1.openFuse();
        assertFalse(f1.isClosed());
        assertTrue(f1.getStatus().isCertain());
        assertFalse(f1.getStatus().isUncertain());
        assertEquals(Confidence.MIN_PROBABILITY, f1.getStatus().confIsOpen());

        f2.setConfIsClosed(0.8);
        assertTrue(f2.isClosed());
        assertFalse(f2.getStatus().isCertain());
        assertTrue(f2.getStatus().isUncertain());
        assertEquals(0.8, f2.getStatus().confIsClosed());
        assertEquals(0.2, f2.getStatus().confIsOpen(), 0.0001);


        var f3 = new Fuse("f3");
        f3.openFuse();
        f3.setConfIsOpen(0.6);
        assertFalse(f3.isClosed());
        assertFalse(f3.getStatus().isCertain());
        assertTrue(f3.getStatus().isUncertain());
        assertEquals(0.4, f3.getStatus().confIsClosed(),0.0001);
        assertEquals(0.6, f3.getStatus().confIsOpen(), 0.0001);
    }


}
