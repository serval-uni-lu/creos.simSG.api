package duc.sg.java.loadapproximator.test.matrixBuilder.dev;

import duc.sg.java.model.Cabinet;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

public abstract class MatrixBuilderTestDev {
    protected Substation substation;
    protected Map<String, Fuse> fusesMap;
    protected Set<Cabinet> cabinets;
    protected Map<String, Integer> idxFuses;
    protected double[] actual;

    @BeforeEach
    public void init() {
        createSubstation();
        initFuseMap();
    }

    protected abstract void createSubstation();

    protected void initFuseMap() {
        fusesMap = new HashMap<>(6);
        cabinets = new HashSet<>();

        var waiting = new Stack<Fuse>();
        waiting.add(substation.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            fusesMap.put(current.getName(), current);

            var opp = current.getOpposite();
            fusesMap.put(opp.getName(), opp);

            var ownerOpp = opp.getOwner();
            if(ownerOpp instanceof Cabinet) {
                cabinets.add((Cabinet) ownerOpp);
            }
            for(var f: ownerOpp.getFuses()) {
                if(!fusesMap.containsKey(f.getName())) {
                    waiting.add(f);
                }
            }
        }
    }

    protected double[] buildMatrix() {
//        var matrixBuilder = new MatrixBuilder();
//        var res = matrixBuilder.build(substation);
//        idxFuses = matrixBuilder.getIdxFuses();
//        return MatrixBuilder.build(substation);
        return null;
    }

    protected void openFuses(String[] toOpen) {
        for (var fName: toOpen) {
            fusesMap.get(fName).openFuse();
        }
    }

    protected void genericTest(double[] expected, String... toOpen) {
        openFuses(toOpen);
        actual = buildMatrix();
        Assertions.assertArrayEquals(expected, actual, 0.1);
    }
}
