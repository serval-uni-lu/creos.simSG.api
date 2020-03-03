package duc.aintea.loadapproximation.test.sg;

import duc.aintea.loadapproximation.test.generator.Data;
import duc.aintea.sg.State;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class StateTest {


    private static Arguments[] values() {
        return Data.generateRandomDoubles(1.);
    }

    @ParameterizedTest
    @MethodSource("values")
    public void testOpen(double confVal) {
        var state = new State(false, 1);
        assertTrue(state.isCertain());
        assertEquals(1, state.getConfOpenAsProb());
        assertEquals(0, state.getConfClosedAsProb());

        state.setConfAsProb(confVal);
        assertFalse(state.isCertain());
        assertEquals(confVal, state.getConfOpenAsProb());
        assertEquals(1-confVal, state.getConfClosedAsProb());

        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(-1));
        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(-0.5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(1.01));

        state.makeCertain();
        assertTrue(state.isCertain());
        assertEquals(1, state.getConfOpenAsProb());
        assertEquals(0, state.getConfClosedAsProb());
    }

    @ParameterizedTest
    @MethodSource("values")
    public void testClosed(double confVal) {
        var state = new State(true, 1);
        assertTrue(state.isCertain());
        assertEquals(1, state.getConfClosedAsProb());
        assertEquals(0, state.getConfOpenAsProb());

        state.setConfAsProb(confVal);
        assertFalse(state.isCertain());
        assertEquals(confVal, state.getConfClosedAsProb());
        assertEquals(1-confVal, state.getConfOpenAsProb());

        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(-1));
        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(-0.5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfAsProb(1.01));

        state.makeCertain();
        assertTrue(state.isCertain());
        assertEquals(1, state.getConfClosedAsProb());
        assertEquals(0, state.getConfOpenAsProb());
    }


//    @Test
//    public void test() {
//        var state = new State(true, 1);
//        System.out.println(state);
//
//        state.close();
//        state.setConfAsProb(0.3);
//        System.out.println(state);
//        System.out.println(state.getConfClosedAsProb());
//        System.out.println(state.getConfOpenAsProb());
//
//        state.open();
//        System.out.println(state);
//        System.out.println(state.getConfClosedAsProb());
//        System.out.println(state.getConfOpenAsProb());
//    }

}
