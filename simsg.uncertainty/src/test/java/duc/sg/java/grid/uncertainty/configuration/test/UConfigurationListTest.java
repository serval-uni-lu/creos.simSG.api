package duc.sg.java.grid.uncertainty.configuration.test;

import duc.sg.java.grid.uncertainty.configuration.UConfigurationList;
import duc.sg.java.grid.uncertainty.configuration.UConfiguration;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.utils.Pair;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UConfigurationListTest {
    private static final Random RANDOM = new Random(98745);


    private void assertUConfiguration(UConfiguration configuration, Map<Fuse, State> expectedStates, double expectedConf) {
        assertEquals(expectedConf, configuration.getConfidence(), 0.00001);
        for(Pair<Fuse, State> fuseState: configuration) {
            assertEquals(expectedStates.get(fuseState.getFirst()), fuseState.getSecond());
        }

    }

    @Test
    public void testAddMap() {
        var nbFuses = 5;
        var fuses = new ArrayList<Fuse>(nbFuses);
        Collections.addAll(fuses, new Fuse("f1"), new Fuse("f2"), new Fuse("f3"),
                new Fuse("f4"), new Fuse("f5"));

        var confMatrix = new UConfigurationList(fuses.toArray(new Fuse[0]));

        var conf1 = new HashMap<Fuse, State>();
        conf1.put(fuses.get(0), State.OPEN);
        conf1.put(fuses.get(1), State.CLOSED);
        conf1.put(fuses.get(2), State.CLOSED);
        conf1.put(fuses.get(3), State.OPEN);
        conf1.put(fuses.get(4), State.OPEN);
        confMatrix.add(new Configuration(conf1), 0.4);

        var conf2 = new HashMap<Fuse, State>();
        conf2.put(fuses.get(0), State.CLOSED);
        conf2.put(fuses.get(1), State.CLOSED);
        conf2.put(fuses.get(2), State.CLOSED);
        conf2.put(fuses.get(3), State.OPEN);
        conf2.put(fuses.get(4), State.OPEN);
        confMatrix.add(new Configuration(conf2), 0.35);

        var conf3 = new HashMap<Fuse, State>();
        conf3.put(fuses.get(0), State.CLOSED);
        conf3.put(fuses.get(1), State.CLOSED);
        conf3.put(fuses.get(2), State.CLOSED);
        conf3.put(fuses.get(3), State.CLOSED);
        conf3.put(fuses.get(4), State.CLOSED);
        confMatrix.add(new Configuration(conf3), 0.15);

        var actual = confMatrix.getConfidences().toArray(new Double[0]);
        assertArrayEquals(new Double[]{0.4, 0.35, 0.15}, actual);
        assertEquals(3, confMatrix.nbConfigurations());

        Iterator<UConfiguration> it = confMatrix.iterator();
        assertUConfiguration(it.next(), conf1, 0.4);
        assertUConfiguration(it.next(), conf2, 0.35);
        assertUConfiguration(it.next(), conf3, 0.15);

        confMatrix.addConfToMaxClosed(0.1);

        it = confMatrix.iterator();
        assertUConfiguration(it.next(), conf1, 0.4);
        assertUConfiguration(it.next(), conf2, 0.35);
        assertUConfiguration(it.next(), conf3, 0.25);
    }

    private static State randomState() {
        return RANDOM.nextBoolean()? State.CLOSED : State.OPEN;
    }

    @Test
    public void testAddOther() {
        var confMatrix = new UConfigurationList();

        // Other 1
        var otherFuse1 = new ArrayList<Fuse>();
        Collections.addAll(otherFuse1, new Fuse("f1"), new Fuse("f2"));
        var other1 = new UConfigurationList(otherFuse1.toArray(new Fuse[0]));
        var confOther1 = new HashMap<Fuse, State>();
        otherFuse1.forEach((Fuse fuse) -> {
            confOther1.put(fuse, randomState());
        });
        var confOther2 = new HashMap<Fuse, State>();
        otherFuse1.forEach((Fuse fuse) -> {
            confOther2.put(fuse, randomState());
        });
        other1.add(new Configuration(confOther1), 0.6);
        other1.add(new Configuration(confOther2), 0.4);
        confMatrix.add(other1);

        Iterator<UConfiguration> it = confMatrix.iterator();
        assertUConfiguration(it.next(), confOther1, 0.6);
        assertUConfiguration(it.next(), confOther2, 0.4);


        // Other 2

        var otherFuse2 = new ArrayList<Fuse>();
        Collections.addAll(otherFuse2, new Fuse("f3"), new Fuse("f4"));
        var other2 = new UConfigurationList(otherFuse2.toArray(new Fuse[0]));
        var confOther3 = new HashMap<Fuse, State>();
        otherFuse2.forEach((Fuse fuse) -> {
            confOther3.put(fuse, randomState());
        });
        var confOther4 = new HashMap<Fuse, State>();
        otherFuse2.forEach((Fuse fuse) -> {
            confOther4.put(fuse, randomState());
        });
        other2.add(new Configuration(confOther3), 0.75);
        other2.add(new Configuration(confOther4), 0.25);
        confMatrix.add(other2);


        var firstLine = new HashMap<Fuse, State>();
        firstLine.putAll(confOther1);
        firstLine.putAll(confOther3);

        var secondLine = new HashMap<Fuse, State>();
        secondLine.putAll(confOther1);
        secondLine.putAll(confOther4);

        var thirdLine = new HashMap<Fuse, State>();
        thirdLine.putAll(confOther2);
        thirdLine.putAll(confOther3);

        var fourthLine = new HashMap<Fuse, State>();
        fourthLine.putAll(confOther2);
        fourthLine.putAll(confOther4);

        Iterator<UConfiguration> it2 = confMatrix.iterator();
        assertUConfiguration(it2.next(), firstLine, 0.45);
        assertUConfiguration(it2.next(), secondLine, 0.15);
        assertUConfiguration(it2.next(), thirdLine, 0.3);
        assertUConfiguration(it2.next(), fourthLine, 0.1);

    }
}
