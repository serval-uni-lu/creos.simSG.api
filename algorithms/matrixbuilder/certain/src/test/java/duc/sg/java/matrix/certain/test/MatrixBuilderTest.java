package duc.sg.java.matrix.certain.test;

import duc.sg.java.matrix.FuseStateMatrix;
import duc.sg.java.matrix.certain.CertainMatrixBuilder;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public abstract class MatrixBuilderTest {
    protected Substation substation;
    protected Map<String, Fuse> fusesMap;


    @BeforeEach
    public void init() {
        createSubstation();
        initFuseMap();
    }

    protected abstract void createSubstation();

    protected void initFuseMap() {
        fusesMap = new HashMap<>();

        var waiting = new ArrayDeque<Fuse>();
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

    protected void openFuses(String[] toOpen) {
        for (var fName: toOpen) {
            fusesMap.get(fName).openFuse();
        }
    }

    protected void genericTest(double[] expected, String... toOpen) {
        openFuses(toOpen);
//        FuseStateMatrix matrix = ((CertainMatrixBuilder)CertainMatrixBuilder.INSTANCE).buildOld(substation, null)[0];
        FuseStateMatrix matrix = CertainMatrixBuilder.INSTANCE.build(substation)[0];
        Assertions.assertArrayEquals(expected,  matrix.getData());
    }
}
