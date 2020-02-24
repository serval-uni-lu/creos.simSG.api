package duc.aintea.loadapproximation.test.utils;

import duc.aintea.sg.Cabinet;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.utils.CycleDetection;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CycleDetectionTests {

    /*
               |-[f1]----(cbl1)----[f2]-|
        subs1-<                         >-c2
               |-[f3]----(cbl2)----[f4]-|
     */
    @Test
    public void testParalleleAtSubs() {
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

        var actualCrclF1 = Arrays.asList(new CycleDetection().getEndCircle(f1));
        assertEquals(4, actualCrclF1.size());
        assertTrue(actualCrclF1.contains(f1), "actualCrclF1 doesn't contain " + f1);
        assertTrue(actualCrclF1.contains(f2), "actualCrclF1 doesn't contain " + f2);
        assertTrue(actualCrclF1.contains(f3), "actualCrclF1 doesn't contain " + f3);
        assertTrue(actualCrclF1.contains(f4), "actualCrclF1 doesn't contain " + f4);

        var actualCrclF3 = Arrays.asList(new CycleDetection().getEndCircle(f3));
        assertEquals(4, actualCrclF3.size());
        assertTrue(actualCrclF3.contains(f1), "actualCrclF3 doesn't contain " + f1);
        assertTrue(actualCrclF3.contains(f2), "actualCrclF3 doesn't contain " + f2);
        assertTrue(actualCrclF3.contains(f3), "actualCrclF3 doesn't contain " + f3);
        assertTrue(actualCrclF3.contains(f4), "actualCrclF3 doesn't contain " + f4);

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

        var actualCrclF1 = Arrays.asList(new CycleDetection().getEndCircle(f1));
        assertEquals(4, actualCrclF1.size());
        assertTrue(actualCrclF1.contains(f1), "actualCrclF1 doesn't contain " + f1);
        assertTrue(actualCrclF1.contains(f2), "actualCrclF1 doesn't contain " + f2);
        assertTrue(actualCrclF1.contains(f3), "actualCrclF1 doesn't contain " + f3);
        assertTrue(actualCrclF1.contains(f4), "actualCrclF1 doesn't contain " + f4);

        var actualCrclF3 = Arrays.asList(new CycleDetection().getEndCircle(f3));
        assertEquals(4, actualCrclF3.size());
        assertTrue(actualCrclF3.contains(f1), "actualCrclF3 doesn't contain " + f1);
        assertTrue(actualCrclF3.contains(f2), "actualCrclF3 doesn't contain " + f2);
        assertTrue(actualCrclF3.contains(f3), "actualCrclF3 doesn't contain " + f3);
        assertTrue(actualCrclF3.contains(f4), "actualCrclF3 doesn't contain " + f4);

        var actualCrclF6 = Arrays.asList(new CycleDetection().getEndCircle(f6));
        assertTrue(actualCrclF6.isEmpty(), "actualCrclF6 is not empty and contains: " + Arrays.toString(actualCrclF6.toArray()));
    }

    /*
               |-[f1]----(cbl1)----]f2[-|
        subs1-<                          >-c2-[f6]----(cbl3)----[f5]-c3
               |-[f3]----(cbl1)----[f4]-|
     */
    @Test
    public void testParallelCablesF2Open() {
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

        f2.openFuse();

        var actualCrclF1 = Arrays.asList(new CycleDetection().getEndCircle(f1));
        assertTrue(actualCrclF1.isEmpty(), "actualCrclF1 is not empty and contains: " + Arrays.toString(actualCrclF1.toArray()));

        var actualCrclF3 = Arrays.asList(new CycleDetection().getEndCircle(f3));
        assertTrue(actualCrclF3.isEmpty(), "actualCrclF3 is not empty and contains: " + Arrays.toString(actualCrclF3.toArray()));

        var actualCrclF6 = Arrays.asList(new CycleDetection().getEndCircle(f6));
        assertTrue(actualCrclF6.isEmpty(), "actualCrclF6 is not empty and contains: " + Arrays.toString(actualCrclF6.toArray()));
    }

    /*
                                         |-[f2]----(cbl1)----[f1]-|
        subs1-[f5]----(cbl3)----[f6]-c2-<                          >-c1
                                         |-[f4]----(cbl2)----[f3]-|
     */
    @Test
    public void testParallelCablesCabEnd() {
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

        var actualCrclF5 = Arrays.asList(new CycleDetection().getEndCircle(f5));
        assertTrue(actualCrclF5.isEmpty(), "actualCrclF5 is not empty and contains: " + Arrays.toString(actualCrclF5.toArray()));

        var actualCrclF2 = Arrays.asList(new CycleDetection().getEndCircle(f2));
        assertEquals(4, actualCrclF2.size());
        assertTrue(actualCrclF2.contains(f1), "actualCrclF2 doesn't contain " + f1);
        assertTrue(actualCrclF2.contains(f2), "actualCrclF2 doesn't contain " + f2);
        assertTrue(actualCrclF2.contains(f3), "actualCrclF2 doesn't contain " + f3);
        assertTrue(actualCrclF2.contains(f4), "actualCrclF2 doesn't contain " + f4);

        var actualCrclF4 = Arrays.asList(new CycleDetection().getEndCircle(f4));
        assertEquals(4, actualCrclF4.size());
        assertTrue(actualCrclF4.contains(f1), "actualCrclF4 doesn't contain " + f1);
        assertTrue(actualCrclF4.contains(f2), "actualCrclF4 doesn't contain " + f2);
        assertTrue(actualCrclF4.contains(f3), "actualCrclF4 doesn't contain " + f3);
        assertTrue(actualCrclF4.contains(f4), "actualCrclF4 doesn't contain " + f4);
    }


    /*
                                         |-[f2]----(cbl1)----[f1]-|
        subs1-[f5]----(cbl3)----[f6]-c2-<                          >-c1-[f7]----(cbl4)----[f8]-c3
                                         |-[f4]----(cbl2)----[f3]-|
     */
    @Test
    public void testParallelCablesSub() {
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");
        Substation subs1 = new Substation("subs1");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");
        Fuse f7 = new Fuse("f7");
        Fuse f8 = new Fuse("f8");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();
        Cable cbl4 = new Cable();

        c1.addFuses(f1, f3, f7);
        c2.addFuses(f2, f4, f6);
        c3.addFuses(f8);
        subs1.addFuses(f5);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);

        var actualCrclF5 = Arrays.asList(new CycleDetection().getEndCircle(f5));
        assertTrue(actualCrclF5.isEmpty(), "actualCrclF5 is not empty and contains: " + Arrays.toString(actualCrclF5.toArray()));

        var actualCrclF2 = Arrays.asList(new CycleDetection().getEndCircle(f2));
        assertEquals(4, actualCrclF2.size());
        assertTrue(actualCrclF2.contains(f1), "actualCrclF2 doesn't contain " + f1);
        assertTrue(actualCrclF2.contains(f2), "actualCrclF2 doesn't contain " + f2);
        assertTrue(actualCrclF2.contains(f3), "actualCrclF2 doesn't contain " + f3);
        assertTrue(actualCrclF2.contains(f4), "actualCrclF2 doesn't contain " + f4);

        var actualCrclF4 = Arrays.asList(new CycleDetection().getEndCircle(f4));
        assertEquals(4, actualCrclF4.size());
        assertTrue(actualCrclF4.contains(f1), "actualCrclF4 doesn't contain " + f1);
        assertTrue(actualCrclF4.contains(f2), "actualCrclF4 doesn't contain " + f2);
        assertTrue(actualCrclF4.contains(f3), "actualCrclF4 doesn't contain " + f3);
        assertTrue(actualCrclF4.contains(f4), "actualCrclF4 doesn't contain " + f4);

        var actualCrclF7 = Arrays.asList(new CycleDetection().getEndCircle(f7));
        assertTrue(actualCrclF7.isEmpty(), "actualCrclF7 is not empty and contains: " + Arrays.toString(actualCrclF7.toArray()));
    }
}
