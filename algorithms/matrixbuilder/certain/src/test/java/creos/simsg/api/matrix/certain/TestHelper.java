package creos.simsg.api.matrix.certain;

import creos.simgsg.api.utils.BaseTransform;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;

class TestHelper {
    static Arguments[] generateAllPossibilities(String... names) {
        int nbSituations =  (int) Math.pow(2, names.length);
        var args = new Arguments[nbSituations];

        for (int i = 0; i < nbSituations; i++) {
            boolean[] fuseStates = BaseTransform.toBinary(i, names.length);
            var fuseNames = new ArrayList<String>(names.length);
            for (int j = 0; j < fuseStates.length; j++) {
                if(fuseStates[j]) {
                    fuseNames.add(names[j]);
                }
            }
            args[i] = Arguments.of((Object) fuseNames.toArray(new String[0]));
        }

        return args;
    }

    static String[] merge(String[] firsts, String... seconds) {
        var res = new String[firsts.length + seconds.length];
        System.arraycopy(firsts, 0, res, 0, firsts.length);
        System.arraycopy(seconds, 0, res, firsts.length, seconds.length);
        return res;
    }

}
