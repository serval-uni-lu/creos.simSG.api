package duc.sg.java.model.test;

import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Meter;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CableTest {


    @Test
    public void uncertainLoadTest() {
        var f1 = new Fuse("f1");
        var uloadF1 = new MultDblePossibilities();
        uloadF1.addOrReplace(new PossibilityDouble(0, 0.1));
        uloadF1.addOrReplace(new PossibilityDouble(20, 0.6));
        uloadF1.addOrReplace(new PossibilityDouble(50, 0.3));
        f1.setLoad(uloadF1);

        var f2 = new Fuse("f2");
        var uloadF2 = new MultDblePossibilities();
        uloadF2.addOrReplace(new PossibilityDouble(15, 0.1));
        uloadF2.addOrReplace(new PossibilityDouble(20, 0.5));
        uloadF2.addOrReplace(new PossibilityDouble(50, 0.2));
        uloadF2.addOrReplace(new PossibilityDouble(60, 0.2));
        f2.setLoad(uloadF2);

        var cable = new Cable();
        cable.setFuses(f1, f2);
        var actuals = cable.getUncertainLoad();
        var expected = new MultDblePossibilities();
        expected.addOrReplace(new PossibilityDouble(15, 0.01));
        expected.addOrReplace(new PossibilityDouble(20, 0.41));
        expected.addOrReplace(new PossibilityDouble(50, 0.38));
        expected.addOrReplace(new PossibilityDouble(60, 0.2));

        int nb=0;
        for (PossibilityDouble actual: actuals) {
            Iterator<PossibilityDouble> itExpected = expected.iterator();
            PossibilityDouble found = null;
            while (itExpected.hasNext() && found==null) {
                var current = itExpected.next();
                if(current.getValue() == actual.getValue()) {
                    found = current;
                }
            }
            assertNotNull(found);
            assertEquals(found.getConfidence().getProbability(), actual.getConfidence().getProbability(), 0.0001);
            nb++;
        }
        assertEquals(4, nb);

        var cable2 = new Cable();
        cable2.setFirstFuse(f1);
        var actCbl2 = cable2.getUncertainLoad();
        assertEquals(uloadF1, actCbl2);

        var cable3 = new Cable();
        cable3.setSecondFuse(f1);
        var actCbl3 = cable3.getUncertainLoad();

    }

    @Test
    public void testConsumptions() {
        var values = new double[]{2, 5, 86, 8764, 654};
        double sum = 0;
        for (var v: values) sum += v;

        var cable = new Cable();
        for (int i = 0; i < values.length; i++) {
            double v = values[i];
            var meter = new Meter("meter_" + i);
            meter.setConsumption(v);
            cable.addMeters(meter);
        }

        assertEquals(values.length, cable.getMeters().size());
        List<Meter> meters = cable.getMeters();
        for (int i = 0; i < meters.size(); i++) {
            Meter meter = meters.get(i);
            assertEquals("meter_" + i, meter.getName());
            assertEquals(values[i], meter.getConsumption());
        }

        assertEquals(sum, cable.getConsumption());

    }


}
