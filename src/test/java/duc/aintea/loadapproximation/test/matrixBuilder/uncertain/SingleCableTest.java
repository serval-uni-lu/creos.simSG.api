package duc.aintea.loadapproximation.test.matrixBuilder.uncertain;

import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.SingleCableBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SingleCableTest extends UncertainMatrixBuilderTest {

    @Test
    public void testF1Uncertain() {
        var possibilities = new ArrayList<double[]>();
        possibilities.add(new double[]{1});
        possibilities.add(new double[]{0});


        genericTest(
                new String[]{SingleCableBuilder.F1_NAME},
                possibilities,
                new int[]{1,1}

        );

    }

    @Test
    public void testF2Uncertain() {
        var possibilities = new ArrayList<double[]>();
        possibilities.add(new double[]{1});

        genericTest(
                new String[]{SingleCableBuilder.F2_NAME},
                possibilities,
                new int[]{1}

        );
    }

    @Test
    public void testF1F2Uncertain() {
        var possibilities = new ArrayList<double[]>();
        possibilities.add(new double[]{1});
        possibilities.add(new double[]{0});


        genericTest(
                new String[]{SingleCableBuilder.F1_NAME, SingleCableBuilder.F2_NAME},
                possibilities,
                new int[]{1,1}

        );
    }

    @Override
    protected Substation createSubstation() {
        return SingleCableBuilder.build();
    }
}
