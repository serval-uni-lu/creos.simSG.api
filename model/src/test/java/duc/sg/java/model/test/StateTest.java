package duc.sg.java.model.test;

import duc.sg.java.model.State;
import duc.sg.java.model.Status;
import duc.sg.java.uncertainty.Bernoulli;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class StateTest {


    private static Arguments[] values() {
        return TestHelper.generateRandomDoubles(1.);
    }

    @ParameterizedTest
    @MethodSource("values")
    public void testOpen(double confVal) {
        var state = new Status(State.OPEN);
        assertTrue(state.isCertain());
        assertFalse(state.isUncertain());
        assertEquals(1, state.confIsOpen());
        assertEquals(0, state.confIsClosed());

        state.setConfIsOpen(confVal);
        assertFalse(state.isCertain());
        assertTrue(state.isUncertain());
        assertEquals(confVal, state.confIsOpen());
        assertEquals(Bernoulli.getOpposite(confVal), state.confIsClosed());

        assertThrows(IllegalArgumentException.class, () -> state.setConfIsClosed(-1));
        assertThrows(IllegalArgumentException.class, () -> state.setConfIsOpen(-0.5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfIsClosed(5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfIsOpen(1.01));

        state.makeCertain();
        assertTrue(state.isCertain());
        assertFalse(state.isUncertain());
        assertEquals(1, state.confIsOpen());
        assertEquals(0, state.confIsClosed());
    }

    @ParameterizedTest
    @MethodSource("values")
    public void testClosed(double confVal) {
        var state = new Status(State.CLOSED);
        assertTrue(state.isCertain());
        assertFalse(state.isUncertain());
        assertEquals(1, state.confIsClosed());
        assertEquals(0, state.confIsOpen());

        state.setConfIsClosed(confVal);
        assertFalse(state.isCertain());
        assertTrue(state.isUncertain());
        assertEquals(confVal, state.confIsClosed());
        assertEquals(Bernoulli.getOpposite(confVal), state.confIsOpen());

        assertThrows(IllegalArgumentException.class, () -> state.setConfIsClosed(-1));
        assertThrows(IllegalArgumentException.class, () -> state.setConfIsOpen(-0.5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfIsClosed(5));
        assertThrows(IllegalArgumentException.class, () -> state.setConfIsOpen(1.01));

        state.makeCertain();
        assertTrue(state.isCertain());
        assertFalse(state.isUncertain());
        assertEquals(1, state.confIsClosed());
        assertEquals(0, state.confIsOpen());
    }


    @ParameterizedTest
    @MethodSource("values")
    public void test(double confVal) {
        var state = new Status(State.CLOSED);

        state.close();
        state.setConfIsClosed(confVal);
        assertEquals(confVal, state.confIsClosed());
        assertEquals(Bernoulli.getOpposite(confVal), state.confIsOpen());

        state.open();
        assertEquals(confVal, state.confIsClosed(), 0.0001);
        assertEquals(Bernoulli.getOpposite(confVal), state.confIsOpen(),0.0001);
    }

}
