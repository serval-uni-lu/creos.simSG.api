package duc.aintea.loadapproximation.matrixBuilder;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;

public class Utils {


    private static boolean[] intToBinary(int data, int size) {
        var res = new boolean[size];
        for (int i = 0; i < res.length ; i++) {
            res[size - 1 -i] = (1 << i & data) != 0;
        }
        return res;
    }

    public static Arguments[] generator(String... names) {
        var args = new Arguments[(int) Math.pow(2, names.length)];

        for (int i = 0; i < args.length; i++) {
            boolean[] fuseStates = intToBinary(i, names.length);

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

    public static String[] merge(String[] firsts, String... seconds) {
        var res = new String[firsts.length + seconds.length];
        System.arraycopy(firsts, 0, res, 0, firsts.length);
        System.arraycopy(seconds, 0, res, firsts.length, seconds.length);
        return res;
    }
}