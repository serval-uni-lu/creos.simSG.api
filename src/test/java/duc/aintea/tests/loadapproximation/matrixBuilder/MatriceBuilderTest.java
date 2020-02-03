package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class MatriceBuilderTest {
    protected Substation substation;
    protected Map<String, Fuse> fusesMap;

    @BeforeEach
    public void init() {
        createSubstation();
        initFuseMap();
    }

    protected abstract void createSubstation();

    protected void initFuseMap() {
        fusesMap = new HashMap<>(6);

        var waiting = new Stack<Fuse>();
        waiting.add(substation.getFuses().get(0));

        while (!waiting.isEmpty()) {
            var current = waiting.pop();
            fusesMap.put(current.getName(), current);

            var opp = current.getOpposite();
            fusesMap.put(opp.getName(), opp);

            var ownerOpp = opp.getOwner();
            for(var f: ownerOpp.getFuses()) {
                if(!fusesMap.containsKey(f.getName())) {
                    waiting.add(f);
                }
            }
        }
    }

    private double[] buildMatrix() {
        return new MatrixBuilder().build(substation);
    }

    private void openFuses(String[] toOpen) {
        for (var fName: toOpen) {
            fusesMap.get(fName).openFuse();
        }
    }

    protected void genericTest(double[] expected, String... toOpen) {
        openFuses(toOpen);
        Assertions.assertArrayEquals(expected, buildMatrix(), 0.1);
    }
}
