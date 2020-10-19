package creos.simsg.api.uncertainty.math;

import creos.simsg.api.uncertainty.PossibilityDouble;
import creos.simsg.api.uncertainty.Confidence;
import creos.simsg.api.uncertainty.MultiplePossibilities;

/**
 * Set of mathematical function to manipulate uncertain data.
 *
 * In current implementations, we consider the variable as
 * <a href="https://en.wikipedia.org/wiki/Independence_(probability_theory)">independent</a> and
 * <a href="https://en.wikipedia.org/wiki/Disjoint_sets">disjoint</a>.
 */
public class UMath {
    private UMath(){}

    /// And
    public static Confidence and(Confidence a, Confidence b) {
        return new Confidence(a.getProbability()*b.getProbability());
    }

    /// Or
    public static Confidence or(Confidence a, Confidence b) {
        return new Confidence(a.getProbability()+b.getProbability());
    }

    public static PossibilityDouble or(PossibilityDouble a, PossibilityDouble b) {
        if(a == null) return b;
        if(b == null) return a;

        if(a.getValue() == b.getValue()) {
            Confidence newConf = UMath.or(a.getConfidence(), b.getConfidence());
            return new PossibilityDouble(a.getValue(), newConf);
        }
        throw new RuntimeException("Not yet implemented");
    }


    /// Max function

    /**
     * Returns the maximal value between two {@link PossibilityDouble}, with its confidence value.
     * The maximum value equals to the maximal of the two value and the confidence to get both values.
     *
     * We do not compute the maximum of the unknown part of the {@link PossibilityDouble} values as we do not know them.
     * So we do not assume anything.
     *
     * @param a first {@link PossibilityDouble} value
     * @param b second {@link PossibilityDouble} value
     * @return the larger between a and b with the confidence that it's larger
     */
    public static PossibilityDouble max(PossibilityDouble a, PossibilityDouble b) {
        var maxValue = Math.max(a.getValue(), b.getValue());
        var confMax = UMath.and(a.getConfidence(), b.getConfidence());
        return new PossibilityDouble(maxValue, confMax);
    }


    /**
     * Returns the larger values between the two different ones. The larger values is composed of
     * the greater values of each cartesian product's element with their confidence, computed by
     * {@link UMath#and}
     *
     * @param a first {@link MultiplePossibilities} value
     * @param b second {@link MultiplePossibilities} value
     * @return larger value
     */
    public static MultiplePossibilities max(MultiplePossibilities a, MultiplePossibilities b) {
        if(a == null) {
            return b;
        }

        if(b == null) {
            return a;
        }

        MultiplePossibilities res = new MultiplePossibilities();
        for(var possA: a) {
            for(var possB: b) {
                res.addPossibility(UMath.max(possA, possB));
            }
        }

        return res.format();
    }

}
