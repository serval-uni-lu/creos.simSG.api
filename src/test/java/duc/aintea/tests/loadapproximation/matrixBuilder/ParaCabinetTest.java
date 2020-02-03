package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.sg.scenarios.ParaCabinetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ParaCabinetTest {

    private Substation substation;
    private Map<String, Fuse> fusesMap;

    private double[] buildMatrix() {
        return new MatrixBuilder().build(substation);
    }

    @BeforeEach
    public void init() {
        substation = ParaCabinetBuilder.build();
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

    @Test
    public void test() {
        buildMatrix();
    }

}
