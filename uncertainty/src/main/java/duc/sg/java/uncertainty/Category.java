package duc.sg.java.uncertainty;

public enum Category {
    UNKNOWN("unknown"),
    MOST_UNLIKELY("Most likely"),
    UNLIKELY("Unlikely"),
    HALF_LIKELY("Half likely"),
    LIKELY("Likely"),
    MOST_LIKELY("Most likely"),
    CERTAIN("Certain");

    private String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static Category probToCategory(double probability) {
        if(probability == Confidence.MIN_PROBABILITY) {
            return UNKNOWN;
        }

        if(probability < 0.1) {
            return MOST_UNLIKELY;
        }

        if(probability < 0.4) {
            return UNLIKELY;
        }

        if(probability < 0.6) {
            return  HALF_LIKELY;
        }

        if (probability < 0.9) {
            return LIKELY;
        }

        if(probability < Confidence.MAX_PROBABILITY) {
            return MOST_LIKELY;
        }

        return CERTAIN;

    }

}
