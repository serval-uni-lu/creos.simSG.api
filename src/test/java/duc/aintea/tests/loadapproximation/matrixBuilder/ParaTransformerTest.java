package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.sg.scenarios.ParaTransformerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static duc.aintea.tests.sg.scenarios.ParaTransformerBuilder.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ParaTransformerTest {
    private Substation substation;
    private Map<String, Fuse> fusesMap;

    @BeforeEach
    public void init() {
        substation = ParaTransformerBuilder.build();
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

    private void openFuses(String... toOpen) {
        for (var fName: toOpen) {
            fusesMap.get(fName).openFuse();
        }
    }

    private static boolean[] intToBinary(int data, int size) {
        var res = new boolean[size];
        for (int i = 0; i < res.length ; i++) {
            res[size - 1 -i] = (1 << i & data) != 0;
        }
        return res;
    }

    private static Arguments[] generator(String... names) {
        var args = new Arguments[(int) Math.pow(2, names.length)];

        for (int i = 0; i < args.length; i++) {
            boolean[] fuseStates = intToBinary(i, names.length);

            var arg = new ArrayList<String>();
            for (int j = 0; j < fuseStates.length; j++) {
                if(fuseStates[j]) {
                    arg.add(names[j]);
                }
            }

            args[i] = Arguments.of((Object) arg.toArray(new String[0]));
        }

        return args;
    }

    private static Arguments[] openCloseF6() {
        return new Arguments[] {
                Arguments.of((Object) new String[0]),
                Arguments.of((Object) new String[]{F6_NAME})
        };
    }

    private static Arguments[] openCloseF4F6() {
        return generator(F6_NAME, F4_NAME);
    }

    private static Arguments[] openCloseF2F6() {
        return generator(F6_NAME, F2_NAME);
    }

    private static Arguments[] openCloseF5F6() {
        return generator(F6_NAME, F5_NAME);
    }

    private static Arguments[] openCloseF4F5F6() {
        return generator(F6_NAME, F5_NAME, F4_NAME);
    }

    private static Arguments[] openCloseF2F5F6() {
        return generator(F6_NAME, F5_NAME, F2_NAME);
    }

    private static Arguments[] openCloseF2F4F5F6() {
        return generator(F6_NAME, F5_NAME, F2_NAME, F4_NAME);
    }


    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc1_testOpenCloseDE(String[] toOpen) {
        openFuses(toOpen);

        var expected = new double[] {
                1,1,0,0,0,
                0,0,1,1,0,
                0,0,0,0,1,
                0,1,0,1,1,
                1,0,-1,0,0,
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F6")
    public void sc2_testF5Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F5_NAME);

        var expected = new double[] {
                1,0,
                0,1
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);

    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc3_testF4OPen(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F4_NAME);

        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,0,1
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc4_testF3Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F3_NAME);

        var expected = new double[] {
          1,1,0,0,
          0,0,1,0,
          0,0,0,1,
          0,1,1,1,
        };

        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc5_testF3F5Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F3_NAME, F5_NAME);

        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };

        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F6")
    public void sc6_testF3F4F5Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F3_NAME, F4_NAME, F5_NAME);

        var expected = new double[] {1};
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc7_testF2Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F2_NAME);

        var expected = new double[] {
                1,0,0,0,
                0,1,1,0,
                0,0,0,1,
                0,0,1,1,
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc8_testF2F5Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F2_NAME, F5_NAME);

        var expected = new double[] {
                1,0,
                0,1,
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF5F6")
    public void sc9_testF2F4Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F2_NAME, F4_NAME);

        var expected = new double[] {
                1,0,
                0,1,
        };
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F5F6")
    public void sc10_testF2F4Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F2_NAME, F3_NAME);

        var expected = new double[] {1};
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc11_testF1Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F1_NAME);

        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1,
        };

        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc12_testF1F5Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F1_NAME, F5_NAME);

        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };

        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F5F6")
    public void sc13_testF1F4Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F1_NAME, F4_NAME);

        var expected = new double[] {1};
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF2F4F5F6")
    public void sc14_testF1F3Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F1_NAME, F3_NAME);

        var expected = new double[]{0};
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc15_testF1F2Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F1_NAME, F2_NAME);

        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };

        assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    @ParameterizedTest
    @MethodSource("openCloseF6")
    public void sc16_testF1F2F5Open(String[] toOpen) {
        openFuses(toOpen);
        openFuses(F1_NAME, F2_NAME, F5_NAME);

        var expected = new double[] {1};
        assertArrayEquals(expected, buildMatrix(), 0.1);
    }



}
