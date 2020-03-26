package duc.sg.java.loadapproximator.test.generator;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;

public class Data {


    public static Arguments[] allUncertainAndOpenPossibilities(String... names) {
        int nbPossibilities = (int) Math.pow(3, names.length);
        var args = new Arguments[nbPossibilities];


        for (int iPoss = 0; iPoss < nbPossibilities; iPoss++) {
            var uFusesNames = new ArrayList<String>();
            var uFusesOpen = new ArrayList<String>();

            int[] possibilities = Utils.toBase3(iPoss, names.length);

            for (int idxPoss = 0; idxPoss < possibilities.length; idxPoss++) {
                if(possibilities[idxPoss] == 0) {
                    uFusesNames.add(names[idxPoss]);
                } else if(possibilities[idxPoss] == 1) {
                    uFusesOpen.add(names[idxPoss]);
                }
            }

            args[iPoss] = Arguments.of(
                    (Object) uFusesNames.toArray(new String[0]),
                    (Object) uFusesOpen.toArray(new String[0])
            );

        }

        return args;
    }


    public static Arguments[] generateAllPossibilities(String... names) {
        return generateAllPossibilitiesWithValues(0, names);
    }

    public static Arguments[] generateAllPossibilitiesWithValues(int nbCable, String... names) {
        int nbSituations =  (int) Math.pow(2, names.length);
        var args = new Arguments[nbSituations * Utils.NB_TESTS];

        for (int i = 0; i < nbSituations; i++) {
            boolean[] fuseStates = Utils.intToBinary(i, names.length);
            var fuseNames = new ArrayList<String>(names.length);
            for (int j = 0; j < fuseStates.length; j++) {
                if(fuseStates[j]) {
                    fuseNames.add(names[j]);
                }
            }

            for (int j = 0; j < Utils.NB_TESTS; j++) {
                args[i* Utils.NB_TESTS + j] = Arguments.of(
                        (Object) fuseNames.toArray(new String[0]),
                        (Object) Utils.randomDouble(nbCable));
            }


        }

        return args;
    }

    public static Arguments[] generateRandomDoubles(int nb) {
        Arguments[] res = new Arguments[Utils.NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object[]) Utils.randomDouble(nb));
        }

        return res;
    }

    public static Arguments[] generateRandomDoubles(double max) {
        Arguments[] res = new Arguments[Utils.NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of(Utils.randomValue(max));
        }

        return res;
    }

    public static Arguments[] generateRandomArrDoubles(int size) {
        Arguments[] res = new Arguments[Utils.NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object) Utils.random_double(size));
        }

        return res;
    }

    public static Arguments[] generateRandomArrBooleans(int nb) {
        Arguments[] res = new Arguments[Utils.NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object) Utils.random_bool(nb));
        }

        return res;
    }

}
