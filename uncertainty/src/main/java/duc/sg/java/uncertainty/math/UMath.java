package duc.sg.java.uncertainty.math;

import duc.sg.java.uncertainty.Confidence;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;

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
    public static PossibilityDouble max(PossibilityDouble a, PossibilityDouble b) {
        var maxValue = Math.max(a.getValue(), b.getValue());
        var confMax = UMath.and(a.getConfidence(), b.getConfidence());
        return new PossibilityDouble(maxValue, confMax);
    }


    public static MultDblePossibilities max(MultDblePossibilities a, MultDblePossibilities b) {
        var res = new MultDblePossibilities();
        for (PossibilityDouble possA : a) {
            for (PossibilityDouble possB : b) {
                PossibilityDouble max = UMath.max(possA, possB);
                res.compute(max, current -> UMath.or(current, max));
            }

        }
        return res;
    }

}
