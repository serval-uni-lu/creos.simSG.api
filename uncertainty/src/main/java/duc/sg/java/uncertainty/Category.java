package duc.sg.java.uncertainty;

public enum Category {
    UNKNOWN(0),
    MOST_UNLIKELY(1),
    UNLIKELY(2),
    HALF_LIKELY(3),
    LIKELY(4),
    MOST_LIKELY(5),
    CERTAIN(6);

    private int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
