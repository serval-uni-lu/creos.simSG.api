package duc.aintea.loadapproximation.generator;

import java.util.Random;

public class Utils {
    private static final double MAX_LOAD = 100.;
    private static final Random RANDOM = new Random(12345);

    static final int NB_TESTS = 5;

    static double randomValue() {
        return RANDOM.nextDouble() * MAX_LOAD;
    }

    static boolean[] intToBinary(int data, int size) {
        var res = new boolean[size];
        for (int i = 0; i < res.length ; i++) {
            res[size - 1 -i] = (1 << i & data) != 0;
        }
        return res;
    }



    public static String[] merge(String[] firsts, String... seconds) {
        var res = new String[firsts.length + seconds.length];
        System.arraycopy(firsts, 0, res, 0, firsts.length);
        System.arraycopy(seconds, 0, res, firsts.length, seconds.length);
        return res;
    }



    static Double[] randomDouble(int nb) {
        var res = new Double[nb];
        for (int j = 0; j < nb; j++) {
            res[j] = randomValue();
        }
        return res;
    }


}
