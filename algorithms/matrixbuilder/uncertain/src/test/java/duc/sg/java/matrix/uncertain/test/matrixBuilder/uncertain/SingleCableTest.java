package duc.sg.java.matrix.uncertain.test.matrixBuilder.uncertain;


import duc.sg.java.model.Substation;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import duc.sg.java.scenarios.SingleCableSC;

import java.util.ArrayList;

public class SingleCableTest extends UncertainMatrixBuilderTest {

//    @Test
    public void testF1Uncertain() {
        var possibilities = new ArrayList<double[]>();
        possibilities.add(new double[]{1});
        possibilities.add(new double[]{0});


        genericTest(
                new String[]{SingleCableSC.F1_NAME},
                possibilities,
                new int[]{1,1}

        );

    }

//    @Test
    public void testF2Uncertain() {
        var possibilities = new ArrayList<double[]>();
        possibilities.add(new double[]{1});

        genericTest(
                new String[]{SingleCableSC.F2_NAME},
                possibilities,
                new int[]{1}

        );
    }

//    @Test
    public void testF1F2Uncertain() {
        var possibilities = new ArrayList<double[]>();
        possibilities.add(new double[]{1});
        possibilities.add(new double[]{0});


        genericTest(
                new String[]{SingleCableSC.F1_NAME, SingleCableSC.F2_NAME},
                possibilities,
                new int[]{1,1}

        );
    }

    @Override
    protected Substation createSubstation() {
        return new ScenarioBuilder()
                .chooseScenario(ScenarioName.SINGLE_CABLE)
                .build()
                .getGrid()
                .getSubstation(SingleCableSC.SUBSTATION_NAME)
                .get();
    }
}
