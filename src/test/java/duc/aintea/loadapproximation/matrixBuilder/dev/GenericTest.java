package duc.aintea.loadapproximation.matrixBuilder.dev;


import org.ejml.data.DenseMatrix64F;
import org.junit.jupiter.api.Assertions;

/**
 * In development.... (???)
 */
public class GenericTest extends MatrixBuilderTestDev {


    public void test() {
        //        var visited = new HashSet<String>();
//        var nbLines2Ones = 0;
//        var nbLines1One = 0;
//        for (var key: fusesMap.keySet()) {
//            var fuse = fusesMap.get(key);
//            var opp = fuse.getOpposite();
//
//            if(!visited.contains(key) && !visited.contains(opp.getName())) {
//                visited.add(key);
//                visited.add(opp.getName());
//
//                if(fuse.isClosed() && opp.isClosed()) {
//                    if(fuse.getOwner().isDeadEnd() || opp.getOwner().isDeadEnd()) {
//                        nbLines1One++;
//                    } else {
//                        nbLines2Ones++;
//                    }
//                } else if(fuse.isClosed() || opp.isClosed()) {
//                    nbLines1One++;
//                }
//            }
//        }
//        System.out.println("Result should contain: ");
//        System.out.println("\t" + nbLines2Ones + " lines with 2 one");
//        System.out.println("\t" + nbLines1One + " lines with 1 one");
//
//        for(var cab: cabinets) {
//            if(cab.getClosedFuses().size() > 1) {
//                System.out.println("\t1 line with " + cab.getClosedFuses().size() + " ones");
//            }
//        }

//        var matrice = new DenseMatrix64F(4,4,true, actual);
//        var ignoredFuses = new ArrayList<String>(fusesMap.size());
//        var knownCycle = new HashSet<Fuse>();
//        var cycleOppToDo = new HashSet<Fuse>();
//
//        for (var key: fusesMap.keySet()) {
//            var fuse = fusesMap.get(key);
//
//            if(fuse.getOwner().isDeadEnd() || !fuse.isClosed()) {
//                ignoredFuses.add(fuse.getName());
//            } else {
//                var nb1 = 1;
//
//                if(fuse.getOwner().getClosedFuses().size() > 1) {
//                    nb1++;
//                }
//
//                if(cycleOppToDo.contains(fuse)) {
//                    nb1++;
//                    cycleOppToDo.remove(fuse);
//                } else if(!knownCycle.contains(fuse)) {
//                    var cycle = new CycleDetection().getEndCircle(fuse);
//                    if(cycle.length > 0) {
//                        Collections.addAll(knownCycle, cycle);
//                        cycleOppToDo.add(cycle[0]);
//                        nb1++;
//                    }
//                }
//
//
//                System.out.println("Column " + idxFuses.get(fuse.getName()) + " should contain " + nb1 + " non-zero lines.");
//            }
//        }
//
//        System.out.println("Matrix should be " + (fusesMap.keySet().size() - ignoredFuses.size()));
//        System.out.println("Ignored fuses: " + Arrays.toString(ignoredFuses.toArray()));



        var matrice = new DenseMatrix64F(4,4,true, actual);
        var nbLinesCables = 0.;
        for (var key: fusesMap.keySet()) {
            var fuse = fusesMap.get(key);
            var oppFuse = fuse.getOpposite();

            if(fuse.getOwner().isDeadEnd() || !fuse.isClosed()) {
                Assertions.assertNull(idxFuses.get(fuse.getName()));
            } else {
                var idxFuse = idxFuses.get(fuse.getName());

                if(oppFuse.getOwner().isDeadEnd() || !oppFuse.isClosed()) {
                    Assertions.assertNull(idxFuses.get(oppFuse.getName()));
                    Assertions.assertEquals(1, matrice.get((int) Math.round((double) idxFuse/2), idxFuse));
                    nbLinesCables++;

                } else {
                    var idxOpp = idxFuses.get(oppFuse.getName());
                    var min = Math.min(idxFuse, idxOpp);
                    Assertions.assertEquals(1, matrice.get(min/2, idxFuse));
                    Assertions.assertEquals(1, matrice.get(min/2, idxOpp));
                    nbLinesCables+=0.5;
                }
            }
        }



        for(var cab: cabinets) {
            if(cab.getClosedFuses().size() > 2) {

            }
        }
    }

    @Override
    protected void createSubstation() {

    }
}
