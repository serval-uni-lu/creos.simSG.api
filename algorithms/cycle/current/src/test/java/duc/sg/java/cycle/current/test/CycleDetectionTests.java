package duc.sg.java.cycle.current.test;

//import duc.sg.java.cycle.all.InitAllCycleSubs;

import duc.sg.java.cycle.all.InitAllCycleSubs2;
import duc.sg.java.cycle.current.CycleDetection;
import duc.sg.java.model.Cabinet;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.BaseTransform;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CycleDetectionTests {

    private void genericTest(Substation substation, Collection<Fuse> allFuses, Fuse start, Collection<Fuse> expected) {
        int nbPossibilities = (int) Math.pow(2, allFuses.size());


        for (int i = 0; i < nbPossibilities; i++) {
            boolean[] fuseState = BaseTransform.toBinary(i, allFuses.size());

            Iterator<Fuse> itFuses = allFuses.iterator();
            boolean allClosed = true;
            for (boolean b : fuseState) {
                Fuse f = itFuses.next();
                if (b) {
                    f.closeFuse();
                } else {
                    f.openFuse();
                }

                if(expected.contains(f)) {
                    allClosed &= b;
                }
            }


            String fuseStateStr = Arrays.toString(fuseState);

            Fuse[] actualArr = CycleDetection.cycleFrom(substation, start);
            List<Fuse> actual = Arrays.asList(actualArr);

            if(allClosed) {
                assertEquals(expected.size(), actual.size(), "Error for: " + fuseStateStr);
                for (Fuse f : expected) {
                    String errorMsg = "Cycle doesn't contain " +
                            f.getName() +
                            ". Start: " +
                            start.getName() +
                            ". Actual: " +
                            Arrays.toString(actualArr) +
                            ". Fuse state: " +
                            fuseStateStr;
                    assertTrue(actual.contains(f), errorMsg);
                }
            } else {
                assertEquals(0, actual.size(), "Error for: " + fuseStateStr);
            }


        }

    }


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

//        InitAllCycleSubs.init(subs1);
        InitAllCycleSubs2.init(subs1);

        Collection<Fuse> allFuses = subs1.extractFuses();
        genericTest(subs1, allFuses, f1, allFuses);
        genericTest(subs1, allFuses, f2, allFuses);
        genericTest(subs1, allFuses, f3, allFuses);
        genericTest(subs1, allFuses, f4, allFuses);

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

        InitAllCycleSubs2.init(subs1);

        Collection<Fuse> allFuses = subs1.extractFuses();
        List<Fuse> cycle = new ArrayList<>();
        Collections.addAll(cycle, f1, f2, f3, f4);
        genericTest(subs1, allFuses, f1, cycle);
        genericTest(subs1, allFuses, f2, cycle);
        genericTest(subs1, allFuses, f3, cycle);
        genericTest(subs1, allFuses, f4, cycle);
        genericTest(subs1, allFuses, f5, new ArrayList<>());
        genericTest(subs1, allFuses, f6, new ArrayList<>());
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

        InitAllCycleSubs2.init(subs1);

        Collection<Fuse> allFuses = subs1.extractFuses();
        List<Fuse> cycle = new ArrayList<>();
        Collections.addAll(cycle, f1, f2, f3, f4);
        genericTest(subs1, allFuses, f1, cycle);
        genericTest(subs1, allFuses, f2, cycle);
        genericTest(subs1, allFuses, f3, cycle);
        genericTest(subs1, allFuses, f4, cycle);
        genericTest(subs1, allFuses, f5, new ArrayList<>());
        genericTest(subs1, allFuses, f6, new ArrayList<>());

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

        InitAllCycleSubs2.init(subs1);

        Collection<Fuse> allFuses = subs1.extractFuses();
        List<Fuse> cycle = new ArrayList<>();
        Collections.addAll(cycle, f1, f2, f3, f4);
        genericTest(subs1, allFuses, f1, cycle);
        genericTest(subs1, allFuses, f2, cycle);
        genericTest(subs1, allFuses, f3, cycle);
        genericTest(subs1, allFuses, f4, cycle);
        genericTest(subs1, allFuses, f5, new ArrayList<>());
        genericTest(subs1, allFuses, f6, new ArrayList<>());
        genericTest(subs1, allFuses, f7, new ArrayList<>());
        genericTest(subs1, allFuses, f8, new ArrayList<>());
    }
}
