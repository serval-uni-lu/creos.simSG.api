package duc.aintea.tests.loadapproximation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LoadApproximationTest {
    @ParameterizedTest
    @MethodSource("duc.aintea.tests.loadapproximation.DataGenerator#oneValue")
    public void scenario1(double il1) {
        Scenario1 sc1 = new Scenario1(il1);

        double[] actual = sc1.computeLoad();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.length);
        Assertions.assertArrayEquals(new double[]{il1, 0}, actual, 0.1);
    }

    @ParameterizedTest
    @MethodSource("duc.aintea.tests.loadapproximation.DataGenerator#threeValues")
    public void scenario2(double il1, double il2, double il3) {
        Scenario2 sc2 = new Scenario2(il1, il2, il3);
        double[] actual = sc2.computeLoad();

        double[] expected = new double[]{
                il1 + il2 + il3,
                -(il2 + il3),
                il2,
                il3,
                0,
                0};

        Assertions.assertNotNull(actual);
        Assertions.assertArrayEquals(expected, actual, 0.1);
    }

    @ParameterizedTest
    @MethodSource("duc.aintea.tests.loadapproximation.DataGenerator#threeValues")
    public void scenario3(double il1, double il2, double il3) {
        Scenario3 sc3 = new Scenario3(il1, il2, il3);
        double[] actual = sc3.computeLoad();

        double[] expected = new double[]{
                il1 - ((il1 - il2 - il3) / 2),
                (il1 - il2 - il3) / 2,
                il1 - ((il1 - il2 - il3) / 2),
                -il1 + il2 + ((il1 - il2 - il3) / 2),
                il3,
                0
        };
        Assertions.assertNotNull(actual);
        Assertions.assertArrayEquals(expected, actual, 0.1);

    }


    @ParameterizedTest
    @MethodSource("duc.aintea.tests.loadapproximation.DataGenerator#fourValues")
    public void scenario4(double il1, double il2, double il3, double il4) {
        Scenario4 sc4 = new Scenario4(il1, il2, il3, il4);
        double[] actual = sc4.computeLoad();

        double[] expected = new double[]{
                il1 + il3 + il2 + il4,
                -il2 - il3 - il4,
                il3 - ((il3 - il2 - il4) / 2),
                ((il3 - il2 - il4) / 2) + il2 - il3,
                il3 - (((il3 - il2 - il4) / 2)),
                ((il3 - il2 - il4) / 2),
                il4,
                0

        };

        Assertions.assertNotNull(actual);
        Assertions.assertArrayEquals(expected, actual, 0.1);
    }

    @ParameterizedTest
    @MethodSource("duc.aintea.tests.loadapproximation.DataGenerator#fiveValues")
    public void scenario5(double il1, double il2, double il3, double il4, double il5) {
        Scenario5 sc5 = new Scenario5(il1, il2, il3, il4, il5);
        double[] actual = sc5.computeLoad();

        double[] expected = new double[]{
                -(il2 - il1 - il5 - il4 - il3) / 2 + il2,
                il1 + (il2 - il1 - il5 - il4 - il3) / 2 - il2,
                il2 - (il2 - il1 - il5 - il4 - il3) / 2,
                (il2 - il1 - il5 - il4 - il3) / 2,
                il3,
                0,
                -(il2 - il1 - il5 - il4 - il3) / 2 - il3,
                il4 + il3 + (il2 - il1 - il5 - il4 - il3) / 2,
                il5,
                0,
        };
        Assertions.assertNotNull(actual);
        Assertions.assertArrayEquals(expected, actual, 0.1);
    }
}
