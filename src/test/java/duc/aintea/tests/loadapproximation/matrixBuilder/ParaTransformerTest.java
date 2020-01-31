package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.sg.scenarios.ParaTransformaterBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ParaTransformerTest {
    private Substation substation;
    private Map<String, Fuse> fusesMap;

    @BeforeEach
    public void init() {
        substation = ParaTransformaterBuilder.build();
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

    @Test
    public void testOpenCloseDE() {
        var expected = new double[] {
                1,1,0,0,0,
                0,0,1,1,0,
                0,0,0,0,1,
                0,1,0,1,1,
                1,0,-1,0,0,
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @Test
    public void testSc2() {
        fusesMap.get("fuse3_cabinet_1").openFuse();
        var expected = new double[] {
                1,0,
                0,1
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);

    }

    @Test
    public void testSc3() {
        fusesMap.get("fuse2_cabinet_1").openFuse();
        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,0,1
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @Test
    public void testSc4() {
        fusesMap.get("fuse2_cabinet_1").openFuse();
        fusesMap.get("fuse3_cabinet_1").openFuse();

        var expected = new double[] {
                1,0,
                0,1
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

}
