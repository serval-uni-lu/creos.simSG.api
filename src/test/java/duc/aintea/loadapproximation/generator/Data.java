package duc.aintea.loadapproximation.generator;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;

import static duc.aintea.loadapproximation.generator.Utils.*;

public class Data {
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
                args[i*NB_TESTS + j] = Arguments.of(
                        (Object) fuseNames.toArray(new String[0]),
                        (Object) randomDouble(nbCable));
            }


        }

        return args;
    }

    public static Arguments[] generateRandomDoubles(int nb) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object[]) randomDouble(nb));
        }

        return res;
    }

    public static Arguments[] generateRandomArrDoubles(int size) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object) random_double(size));
        }

        return res;
    }

    public static Arguments[] generateRandomArrBooleans(int nb) {
        Arguments[] res = new Arguments[NB_TESTS];

        for (int i = 0; i < res.length; i++) {
            res[i] = Arguments.of((Object) random_bool(nb));
        }

        return res;
    }

}
