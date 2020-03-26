package duc.sg.java.loadapproximator.test.generator;

import java.util.ArrayList;
import java.util.Random;

public class Utils {
    private static final double MAX_LOAD = 100.;
    private static final Random RANDOM = new Random(12345);

    static final int NB_TESTS = 5;

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

    static double[] random_double(int nb) {
        var res = new double[nb];
        for (int j = 0; j < nb; j++) {
            res[j] = randomValue(MAX_LOAD);
        }
        return res;
    }

    static Boolean[] randomBool(int nb) {
        var res = new Boolean[nb];
        for (int j = 0; j < nb; j++) {
            res[j] = RANDOM.nextBoolean();
        }
        return res;
    }

    static boolean[] random_bool(int nb) {
        var res = new boolean[nb];
        for (int j = 0; j < nb; j++) {
            res[j] = RANDOM.nextBoolean();
        }
        return res;
    }


}
