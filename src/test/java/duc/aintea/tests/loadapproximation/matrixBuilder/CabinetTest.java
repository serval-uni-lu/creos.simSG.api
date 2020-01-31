package duc.aintea.tests.loadapproximation.matrixBuilder;

import duc.aintea.tests.loadapproximation.MatrixBuilder;
import duc.aintea.tests.sg.Fuse;
import duc.aintea.tests.sg.Substation;
import duc.aintea.tests.sg.scenarios.CabinetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

public class CabinetTest {
    private Substation substation;
    private Map<String, Fuse> fusesMap;

    @BeforeEach
    public void init() {
        substation = CabinetBuilder.build(0);
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

    private static Arguments[] openCloseDEFuses() {
        var args = new Arguments[4];

        args[0] = Arguments.of((Object) new String[0]); //all closed
        args[1] = Arguments.of((Object) new String[]{"fuse_cabinet2"}); //f5 open
        args[2] = Arguments.of((Object) new String[]{"fuse_cabinet3"}); //f6 open
        args[3] = Arguments.of((Object) new String[]{"fuse_cabinet2", "fuse_cabinet3"}); //all open

        return args;
    }

    @ParameterizedTest
    @MethodSource("openCloseDEFuses")
    public void testPossibilitiesDEs(String[] toOpen) {
        openFuses(toOpen);

        var expected = new double[] {
                1,1,0,0,
                0,0,1,0,
                0,0,0,1,
                0,1,1,1,
        };
        Assertions.assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    private static boolean[] intToBinary(int data, int size) {
        var res = new boolean[size];
        for (int i = 0; i < res.length ; i++) {
            res[size - 1 -i] = (1 << i & data) != 0;
        }
        return res;
    }

    private static Arguments[] openCloseF4F3DEFuses() {
        var args = new Arguments[8];

        for (int i = 0; i < 4; i++) {
            boolean[] fuseStates = intToBinary(i, 2);
            var arg = new ArrayList<String>();

            if(fuseStates[0]) {
                arg.add("fuse_cabinet2");
            }

            if(fuseStates[1]) {
                arg.add("fuse_cabinet3");
            }

            arg.add("fuse3_cabinet_1");

            args[i] = Arguments.of((Object) arg.toArray(new String[0]));
        }

        for (int i = 4; i < args.length; i++) {
            boolean[] fuseStates = intToBinary(i-4, 2);
            var arg = new ArrayList<String>();

            if(fuseStates[0]) {
                arg.add("fuse_cabinet2");
            }

            if(fuseStates[1]) {
                arg.add("fuse_cabinet3");
            }

            arg.add("fuse2_cabinet_1");

            args[i] = Arguments.of((Object) arg.toArray(new String[0]));
        }

        return args;
    }

    @ParameterizedTest
    @MethodSource("openCloseF4F3DEFuses")
    public void testPossibilitiesF4F3Open(String[] toOpen) {
        openFuses(toOpen);

        var expected = new double[] {
                1,1,0,
                0,0,1,
                0,1,1,
        };
        Assertions.assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    private static Arguments[] c2DE() {
        var args = new Arguments[4];

        for (int i = 0; i < args.length; i++) {
            boolean[] fuseStates = intToBinary(i, 2);
            var arg = new ArrayList<String>();

            if(fuseStates[0]) {
                arg.add("fuse_cabinet2");
            }

            if(fuseStates[1]) {
                arg.add("fuse_cabinet3");
            }

            arg.add("fuse3_cabinet_1");
            arg.add("fuse2_cabinet_1");

            args[i] = Arguments.of((Object) arg.toArray(new String[0]));
        }



        return args;
    }

    @ParameterizedTest
    @MethodSource("c2DE")
    public void testPossibilitiesC1DE(String[] toOpen) {
        openFuses(toOpen);

        var expected = new double[] {1};
        Assertions.assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    private static Arguments[] allButF1() {
        var args = new Arguments[16];

        for (int i = 0; i < args.length; i++) {
            boolean[] fuseStates = intToBinary(i, 4);
            var arg = new ArrayList<String>();

            if(fuseStates[0]) {
                arg.add("fuse_cabinet2");
            }

            if(fuseStates[1]) {
                arg.add("fuse_cabinet3");
            }

            if(fuseStates[2]) {
                arg.add("fuse3_cabinet_1");
            }

            if(fuseStates[3]) {
                arg.add("fuse2_cabinet_1");
            }

            arg.add("fuse1_cabinet_1");

            args[i] = Arguments.of((Object) arg.toArray(new String[0]));
        }



        return args;
    }

    @ParameterizedTest
    @MethodSource("allButF1")
    public void testAllClosedButF1(String[] toOpen) {
        openFuses(toOpen);

        var expected = new double[] {1};
        Assertions.assertArrayEquals(expected, buildMatrix(), 0.1);
    }

    private static Arguments[] all() {
        var args = new Arguments[32];

        for (int i = 0; i < args.length; i++) {
            boolean[] fuseStates = intToBinary(i, 5);
            var arg = new ArrayList<String>();

            if(fuseStates[0]) {
                arg.add("fuse_cabinet2");
            }

            if(fuseStates[1]) {
                arg.add("fuse_cabinet3");
            }

            if(fuseStates[2]) {
                arg.add("fuse3_cabinet_1");
            }

            if(fuseStates[3]) {
                arg.add("fuse2_cabinet_1");
            }

            if(fuseStates[4]) {
                arg.add("fuse1_cabinet_1");
            }

            arg.add("fuse_subs");

            args[i] = Arguments.of((Object) arg.toArray(new String[0]));
        }



        return args;
    }

    @ParameterizedTest
    @MethodSource("all")
    public void testAll(String[] toOpen) {
        openFuses(toOpen);

        var expected = new double[] {0};
        Assertions.assertArrayEquals(expected, buildMatrix(), 0.1);
    }

}
