package duc.sg.java.circle.all.test;

import duc.sg.java.circle.all.Circle;
import duc.sg.java.circle.all.CircleFinder;
import duc.sg.java.circle.all.CircleUtils;
import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.model.*;
import duc.sg.java.utils.BaseTransform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CircleUtilsTests {

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

            Optional<Circle> actualArr = CircleUtils.circleFrom(substation, start);

            if(expected.size() == 0) {
                assertTrue(actualArr.isEmpty());
            } else {
                assertTrue(actualArr.isPresent());

                Circle actArr = actualArr.get();

                List<Fuse> actual = Arrays.asList(actArr.getFuses());

                if(allClosed) {
                    assertTrue(actArr.isValid());
                    assertEquals(expected.size(), actual.size(), "Error for: " + fuseStateStr);
                    for (Fuse f : expected) {
                        String errorMsg = "circle doesn't contain " +
                                f.getName() +
                                ". Start: " +
                                start.getName() +
                                ". Actual: " +
                                Arrays.toString(actArr.getFuses()) +
                                ". Fuse state: " +
                                fuseStateStr;
                        assertTrue(actual.contains(f), errorMsg);
                    }
                } else {
                    assertFalse(actArr.isValid());
                }

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

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(subs1);
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

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(subs1);
        List<Fuse> circle = new ArrayList<>();
        Collections.addAll(circle, f1, f2, f3, f4);
        genericTest(subs1, allFuses, f1, circle);
        genericTest(subs1, allFuses, f2, circle);
        genericTest(subs1, allFuses, f3, circle);
        genericTest(subs1, allFuses, f4, circle);
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

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(subs1);
        List<Fuse> circle = new ArrayList<>();
        Collections.addAll(circle, f1, f2, f3, f4);
        genericTest(subs1, allFuses, f1, circle);
        genericTest(subs1, allFuses, f2, circle);
        genericTest(subs1, allFuses, f3, circle);
        genericTest(subs1, allFuses, f4, circle);
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

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(subs1);
        List<Fuse> circle = new ArrayList<>();
        Collections.addAll(circle, f1, f2, f3, f4);
        genericTest(subs1, allFuses, f1, circle);
        genericTest(subs1, allFuses, f2, circle);
        genericTest(subs1, allFuses, f3, circle);
        genericTest(subs1, allFuses, f4, circle);
        genericTest(subs1, allFuses, f5, new ArrayList<>());
        genericTest(subs1, allFuses, f6, new ArrayList<>());
        genericTest(subs1, allFuses, f7, new ArrayList<>());
        genericTest(subs1, allFuses, f8, new ArrayList<>());
    }

    /*
                                         |-[f3]----(cbl2)----[f4]-|      |-[f7]----(cbl4)----[f8]--|
        subs1-[f1]----(cbl1)----[f2]-c1-<                          >-c2-<                          >-c3-[f11]----(cbl6)----[f12]-c4
                                         |-[f5]----(cbl3)----[f6]-|      |-[f9]----(cbl5)----[f10]-|
     */
    @Test
    public void testSuccessivePara() {
        Substation subs1 = new Substation("subs1");
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");
        Cabinet c4 = new Cabinet("c4");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");
        Fuse f7 = new Fuse("f7");
        Fuse f8 = new Fuse("f8");
        Fuse f9 = new Fuse("f9");
        Fuse f10 = new Fuse("f10");
        Fuse f11 = new Fuse("f11");
        Fuse f12 = new Fuse("f12");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();
        Cable cbl4 = new Cable();
        Cable cbl5 = new Cable();
        Cable cbl6 = new Cable();

        subs1.addFuses(f1);
        c1.addFuses(f2, f3, f5);
        c2.addFuses(f4, f6, f7, f9);
        c3.addFuses(f8, f10, f11);
        c4.addFuses(f12);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);
        cbl6.setFuses(f11, f12);

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Circle> allCircles = CircleFinder.getDefault().getCircles(subs1);
        assertEquals(2, allCircles.size());

        var expectedFirst = new HashSet<Fuse>(4);
        Collections.addAll(expectedFirst, f3, f4, f5, f6);
        assertSuccPara(allCircles.get(0), expectedFirst);

        var expectedSecond = new HashSet<Fuse>(4);
        Collections.addAll(expectedSecond, f7, f8, f9, f10);
        assertSuccPara(allCircles.get(1), expectedSecond);

    }

    private void assertSuccPara(Circle circle, Set<Fuse> expected) {
        assertEquals(expected.size(), circle.getFuses().length);
        for (Fuse f: circle.getFuses()) {
            assertTrue(expected.contains(f));
        }
    }

    /*
                                         |-[f3]-------------(cbl2)-------------[f4]-c2-[f5]-------------(cbl3)-------------[f6]-|
        subs1-[f1]----(cbl1)----[f2]-c1-<                                                                                        >-c5
                                         |-[f7]------(cbl4)-----[f8]-c3-[f9]-----(cbl5)-----[f10]-c4-[f11]-----(cbl6)-----[f12]-|

     */
    @Test
    public void testIndirectPara() {
        Substation subs1 = new Substation("subs1");
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");
        Cabinet c4 = new Cabinet("c4");
        Cabinet c5 = new Cabinet("c4");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");
        Fuse f7 = new Fuse("f7");
        Fuse f8 = new Fuse("f8");
        Fuse f9 = new Fuse("f9");
        Fuse f10 = new Fuse("f10");
        Fuse f11 = new Fuse("f11");
        Fuse f12 = new Fuse("f12");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();
        Cable cbl4 = new Cable();
        Cable cbl5 = new Cable();
        Cable cbl6 = new Cable();

        subs1.addFuses(f1);
        c1.addFuses(f2, f3, f7);
        c2.addFuses(f4, f5);
        c3.addFuses(f8, f9);
        c4.addFuses(f10, f11);
        c5.addFuses(f6, f12);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);
        cbl6.setFuses(f11, f12);

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Fuse> allFuses = FuseExtracter.INSTANCE.getExtracted(subs1);
        List<Fuse> circle = new ArrayList<>();
        Collections.addAll(circle, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12);

        genericTest(subs1, allFuses, f1, new ArrayList<>());
        genericTest(subs1, allFuses, f2, new ArrayList<>());
        genericTest(subs1, allFuses, f3, circle);
        genericTest(subs1, allFuses, f4, circle);
        genericTest(subs1, allFuses, f5, circle);
        genericTest(subs1, allFuses, f6, circle);
        genericTest(subs1, allFuses, f7, circle);
        genericTest(subs1, allFuses, f8, circle);
        genericTest(subs1, allFuses, f9, circle);
        genericTest(subs1, allFuses, f10, circle);
        genericTest(subs1, allFuses, f11, circle);
        genericTest(subs1, allFuses, f12, circle);
    }


    /*
                                         |-[f3]-------------(cbl2)-------------[f4]-c2-[f5]-------------(cbl3)--------------[f6]-|
        subs1-[f1]----(cbl1)----[f2]-c1-<                                                                                        |
                                         |                            |-[f9]----(cbl5)----[f10]---|                               >-c5
                                         |-[f7]----(cbl4)----[f8]-c3-<                             >-c4-[f13]----(cbl7)----[f14]-|
                                                                      |-[f11]----(cbl6)----[f12]--|
     */
    @Test
    public void testInnerSimplecircle() {
        Substation subs1 = new Substation("subs1");
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");
        Cabinet c4 = new Cabinet("c4");
        Cabinet c5 = new Cabinet("c5");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");
        Fuse f7 = new Fuse("f7");
        Fuse f8 = new Fuse("f8");
        Fuse f9 = new Fuse("f9");
        Fuse f10 = new Fuse("f10");
        Fuse f11 = new Fuse("f11");
        Fuse f12 = new Fuse("f12");
        Fuse f13 = new Fuse("f13");
        Fuse f14 = new Fuse("f14");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();
        Cable cbl4 = new Cable();
        Cable cbl5 = new Cable();
        Cable cbl6 = new Cable();
        Cable cbl7 = new Cable();

        subs1.addFuses(f1);
        c1.addFuses(f2, f3, f7);
        c2.addFuses(f4, f5);
        c3.addFuses(f8, f9, f11);
        c4.addFuses(f10, f12, f13);
        c5.addFuses(f6, f14);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);
        cbl6.setFuses(f11, f12);
        cbl7.setFuses(f13, f14);

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Circle> allCircles = CircleFinder.getDefault().getCircles(subs1);

        var expectedFirst = new HashSet<Fuse>();
        Collections.addAll(expectedFirst, f9, f10, f11, f12);
        var expectedSecond = new HashSet<Fuse>();
        Collections.addAll(expectedSecond, f9, f10, f11, f12, f3, f4, f5, f6, f7, f8, f13, f14);

        assertEquals(2, allCircles.size());

        for (Circle actual: allCircles) {
            if(actual.getFuses().length == expectedFirst.size()) {
                assertSuccPara(actual, expectedFirst);
            } else if(actual.getFuses().length == expectedSecond.size()) {
                assertSuccPara(actual, expectedSecond);
            } else {
                Assertions.fail("Actual circle does not match any size of the expected ones. Actual: " +
                        actual.getFuses().length + ". Expected: [" + expectedFirst.size() + ", " +
                        expectedSecond.size() + "]");
            }
        }
    }

    /*
                                         |-[f3]-------------------(cbl2)--------------------[f4]-c2-[f5]--------------------(cbl3)---------------------[f6]-|
        subs1-[f1]----(cbl1)----[f2]-c1-<                                                                                                                   |
                                         |                            |--[f9]----(cbl5)----[f10]-c4-[f11]----(cbl6)----[f12]-|                               >-c7
                                         |-[f7]----(cbl4)----[f8]-c3-<                                                        >-c6-[f17]----(cbl9)----[f18]-|
                                                                      |-[f13]----(cbl7)----[f14]-c5-[f15]----(cbl8)----[f16]-|
     */
    @Test
    public void testInnerComplexcircle() {
        Substation subs1 = new Substation("subs1");
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");
        Cabinet c4 = new Cabinet("c4");
        Cabinet c5 = new Cabinet("c5");
        Cabinet c6 = new Cabinet("c6");
        Cabinet c7 = new Cabinet("c7");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");
        Fuse f7 = new Fuse("f7");
        Fuse f8 = new Fuse("f8");
        Fuse f9 = new Fuse("f9");
        Fuse f10 = new Fuse("f10");
        Fuse f11 = new Fuse("f11");
        Fuse f12 = new Fuse("f12");
        Fuse f13 = new Fuse("f13");
        Fuse f14 = new Fuse("f14");
        Fuse f15 = new Fuse("f15");
        Fuse f16 = new Fuse("f16");
        Fuse f17 = new Fuse("f17");
        Fuse f18 = new Fuse("f18");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();
        Cable cbl4 = new Cable();
        Cable cbl5 = new Cable();
        Cable cbl6 = new Cable();
        Cable cbl7 = new Cable();
        Cable cbl8 = new Cable();
        Cable cbl9 = new Cable();

        subs1.addFuses(f1);
        c1.addFuses(f2, f3, f7);
        c2.addFuses(f4, f5);
        c3.addFuses(f8, f9, f13);
        c4.addFuses(f10, f11);
        c5.addFuses(f14, f15);
        c6.addFuses(f12, f16, f17);
        c7.addFuses(f6, f18);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);
        cbl6.setFuses(f11, f12);
        cbl7.setFuses(f13, f14);
        cbl8.setFuses(f15, f16);
        cbl9.setFuses(f17, f18);

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Circle> allCircles = CircleFinder.getDefault().getCircles(subs1);
        assertEquals(2, allCircles.size());

        var expectedFirst = new HashSet<Fuse>();
        Collections.addAll(expectedFirst, f11, f12, f16, f15, f14, f13, f9, f10);
        var expectedSecond = new HashSet<Fuse>();
        Collections.addAll(expectedSecond, f18, f17, f12, f11, f10, f9, f8, f7, f3, f4, f5, f6, f16, f15, f14, f13);

        for (Circle actual: allCircles) {
            if(actual.getFuses().length == expectedFirst.size()) {
                assertSuccPara(actual, expectedFirst);
            } else if(actual.getFuses().length == expectedSecond.size()) {
                assertSuccPara(actual, expectedSecond);
            } else {
                Assertions.fail("Actual circle does not match any size of the expected ones. Actual: " +
                        actual.getFuses().length + ". Expected: [" + expectedFirst.size() + ", " +
                        expectedSecond.size() + "]");
            }
        }
    }

    /*
                                         |-[f3]-------------------(cbl2)--------------------[f4]-c2-[f5]--------------------(cbl3)---------------------[f6]-|
        subs1-[f1]----(cbl1)----[f2]-c1-<                                                                                                                   |
                                         |                            |--[f9]------------------(cbl5)------------------[f10]-|                               >-c7
                                         |-[f7]----(cbl4)----[f8]-c3-<                                                        >-c6-[f17]----(cbl9)----[f18]-|
                                                                      |-[f13]----(cbl7)----[f14]-c5-[f15]----(cbl8)----[f16]-|
     */
    @Test
    public void testInnerComplex2circle() {
        Substation subs1 = new Substation("subs1");
        Cabinet c1 = new Cabinet("c1");
        Cabinet c2 = new Cabinet("c2");
        Cabinet c3 = new Cabinet("c3");
        Cabinet c5 = new Cabinet("c5");
        Cabinet c6 = new Cabinet("c6");
        Cabinet c7 = new Cabinet("c7");

        Fuse f1 = new Fuse("f1");
        Fuse f2 = new Fuse("f2");
        Fuse f3 = new Fuse("f3");
        Fuse f4 = new Fuse("f4");
        Fuse f5 = new Fuse("f5");
        Fuse f6 = new Fuse("f6");
        Fuse f7 = new Fuse("f7");
        Fuse f8 = new Fuse("f8");
        Fuse f9 = new Fuse("f9");
        Fuse f10 = new Fuse("f10");
        Fuse f13 = new Fuse("f13");
        Fuse f14 = new Fuse("f14");
        Fuse f15 = new Fuse("f15");
        Fuse f16 = new Fuse("f16");
        Fuse f17 = new Fuse("f17");
        Fuse f18 = new Fuse("f18");

        Cable cbl1 = new Cable();
        Cable cbl2 = new Cable();
        Cable cbl3 = new Cable();
        Cable cbl4 = new Cable();
        Cable cbl5 = new Cable();
        Cable cbl7 = new Cable();
        Cable cbl8 = new Cable();
        Cable cbl9 = new Cable();

        subs1.addFuses(f1);
        c1.addFuses(f2, f3, f7);
        c2.addFuses(f4, f5);
        c3.addFuses(f8, f9, f13);
        c5.addFuses(f14, f15);
        c6.addFuses(f10,f16, f17);
        c7.addFuses(f6, f18);

        cbl1.setFuses(f1, f2);
        cbl2.setFuses(f3, f4);
        cbl3.setFuses(f5, f6);
        cbl4.setFuses(f7, f8);
        cbl5.setFuses(f9, f10);
        cbl7.setFuses(f13, f14);
        cbl8.setFuses(f15, f16);
        cbl9.setFuses(f17, f18);

        SmartGrid grid = new SmartGrid();
        grid.addSubstations(subs1);
        CircleFinder.getDefault().findAndSave(subs1);

        List<Circle> allCircles = CircleFinder.getDefault().getCircles(subs1);
        assertEquals(2, allCircles.size());

        var expectedFirst = new HashSet<Fuse>();
        Collections.addAll(expectedFirst, f16, f15, f14, f13, f9, f10);

        var expectedSecond = new HashSet<Fuse>();
        Collections.addAll(expectedSecond, f18, f17, f10, f9, f8, f7, f3, f4, f5, f6, f16, f15, f14, f13);

        for (Circle actual: allCircles) {
            if(actual.getFuses().length == expectedFirst.size()) {
                assertSuccPara(actual, expectedFirst);
            } else if(actual.getFuses().length == expectedSecond.size()) {
                assertSuccPara(actual, expectedSecond);
            } else {
                Assertions.fail("Actual circle does not match any size of the expected ones. Actual: " +
                        actual.getFuses().length + ". Expected: [" + expectedFirst.size() + ", " +
                        expectedSecond.size() + "]");
            }
        }
    }

}
