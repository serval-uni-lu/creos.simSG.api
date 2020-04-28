package duc.sg.java.matrix.certain.test;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Random;

public class TestHelper {


    static final int NB_TESTS = 5;
    private static final double MAX_LOAD = 100.;
    private static final Random RANDOM = new Random(12345);

    public static Arguments[] allUncertainAndOpenPossibilities(String... names) {
        int nbPossibilities = (int) Math.pow(3, names.length);
        var args = new Arguments[nbPossibilities];


        for (int iPoss = 0; iPoss < nbPossibilities; iPoss++) {
            var uFusesNames = new ArrayList<String>();
            var uFusesOpen = new ArrayList<String>();

            int[] possibilities = toBase3(iPoss, names.length);

            for (int idxPoss = 0; idxPoss < possibilities.length; idxPoss++) {
                if(possibilities[idxPoss] == 0) {
                    uFusesNames.add(names[idxPoss]);
                } else if(possibilities[idxPoss] == 1) {
                    uFusesOpen.add(names[idxPoss]);
                }
            }

            args[iPoss] = Arguments.of(
                    uFusesNames.toArray(new String[0]),
                    uFusesOpen.toArray(new String[0])
            );

        }

        return args;
    }


    public static Arguments[] generateAllPossibilities(String... names) {
        return generateAllPossibilitiesWithValues(0, names);
    }

    public static Arguments[] generateAllPossibilitiesWithValues(int nbCable, String... names) {
        int nbSituations =  (int) Math.pow(2, names.length);
        var args = new Arguments[nbSituations * NB_TESTS];

        for (int i = 0; i < nbSituations; i++) {
            boolean[] fuseStates = intToBinary(i, names.length);
            var fuseNames = new ArrayList<String>(names.length);
            for (int j = 0; j < fuseStates.length; j++) {
                if(fuseStates[j]) {
                    fuseNames.add(names[j]);
                }
            }

            for (int j = 0; j < NB_TESTS; j++) {
                args[i* NB_TESTS + j] = Arguments.of(
                        fuseNames.toArray(new String[0]),
                        randomDouble(nbCable));
            }


        }

        return args;
    }

    public static Arguments[] generateRandomDoubles(int nb) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of(randomDouble(nb));
        }

        return res;
    }

    public static double randomValue(double max) {
        return RANDOM.nextDouble() * max;
    }

    static boolean[] intToBinary(int data, int size) {
        var res = new boolean[size];
        for (int i = 0; i < res.length ; i++) {
            res[size - 1 -i] = (1 << i & data) != 0;
        }
        return res;
    }

    public static int[] toBase3(int data, int size) {
        int tmp;
        var res = new ArrayList<Integer>();
        while (data > 0) {
            tmp = (data / 3);
            res.add(data - 3*tmp);
            data = tmp;
        }

        var resArr = new int[size];
        var end = Math.min(res.size(), size);
        for (int i = 0; i < end; i++) {
            resArr[resArr.length - i - 1] = res.get(i);
        }

        return resArr;
    }

    public static String[] merge(String[] firsts, String... seconds) {
        var res = new String[firsts.length + seconds.length];
        System.arraycopy(firsts, 0, res, 0, firsts.length);
        System.arraycopy(seconds, 0, res, firsts.length, seconds.length);
        return res;
    }

    static Double[] randomDouble(int nb, double max) {
        var res = new Double[nb];
        for (int j = 0; j < nb; j++) {
            res[j] = randomValue(max);
        }
        return res;
    }

    static Double[] randomDouble(int nb) {
        return randomDouble(nb, MAX_LOAD);
    }
}
