package duc.sg.java.model.test;

import duc.sg.java.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OppositeTest {
    private Entity subs1, c2;
    private Fuse f1, f2;
    private Map<String, Fuse> fusesMap;

    /*
        Subs1--[f1]----(cbl1)----[f2]--c2
    */
    @BeforeEach
    public void init() {
        fusesMap = new HashMap<>(2);
        Cable cbl1 = new Cable();

        f1 = new Fuse("f1");
        fusesMap.put("f1", f1);
        f2 = new Fuse("f2");
        fusesMap.put("f2", f2);
        cbl1.setFirstFuse(f1);
        cbl1.setSecondFuse(f2);

        subs1 = new Substation("subs");
        c2 = new Cabinet("c2");
        subs1.addFuses(f1);
        c2.addFuses(f2);
    }

    private void openFuses(String[] toOpen) {
        for (var fName: toOpen) {
            fusesMap.get(fName).openFuse();
        }
    }

    private static Arguments[] openCloseFuses() {
        var args = new Arguments[4];

        args[0] = Arguments.of((Object) new String[0]); //all closed
        args[1] = Arguments.of((Object) new String[]{"f1"}); //f5 open
        args[2] = Arguments.of((Object) new String[]{"f2"}); //f6 open
        args[3] = Arguments.of((Object) new String[]{"f1", "f2"}); //all open

        return args;
    }


    /*
        Subs1--[f1]----(cbl1)----[f2]--c2
    */
    @ParameterizedTest
    @MethodSource("openCloseFuses")
    public void testOpposite(String[] toOpen) {
        openFuses(toOpen);

        Fuse oppositeF1 = f1.getOpposite();
        Fuse oppositeF2 = f2.getOpposite();

        assertEquals(f2, oppositeF1);
        assertEquals(f1, oppositeF2);
    }
}
