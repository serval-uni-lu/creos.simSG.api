package duc.sg.java.utils;

import java.util.List;

public class ListConverter {
    private ListConverter(){}


    public static double[] convert(List<Double> toConvert) {
        if(toConvert == null || toConvert.isEmpty()) {
            return new double[0];
        }

        double[] res = new double[toConvert.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = toConvert.get(i);
        }
        return res;

    }

}
