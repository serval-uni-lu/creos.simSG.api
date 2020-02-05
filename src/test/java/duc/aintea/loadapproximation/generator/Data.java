package duc.aintea.loadapproximation.generator;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;

import static duc.aintea.loadapproximation.generator.Utils.*;

public class Data {
    public static Arguments[] generateAllPossibilities(String... names) {
        return generateAllPossibilitiesWithValues(0, names);
    }

    public static Arguments[] generateAllPossibilitiesWithValues(int nbCable, String... names) {
        var args = new Arguments[(int) Math.pow(2, names.length)];

        for (int i = 0; i < args.length; i++) {
            boolean[] fuseStates = intToBinary(i, names.length);
            var fuseNames = new ArrayList<String>(names.length);
            for (int j = 0; j < fuseStates.length; j++) {
                if(fuseStates[j]) {
                    fuseNames.add(names[j]);
                }
            }

            args[i] = Arguments.of(
                    (Object) fuseNames.toArray(new String[0]),
                    (Object) randomDouble(nbCable));
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

}
