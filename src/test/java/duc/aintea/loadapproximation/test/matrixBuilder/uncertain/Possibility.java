package duc.aintea.loadapproximation.test.matrixBuilder.uncertain;

import java.util.Arrays;

class Possibility {
    double[] data;

    Possibility(double[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Possibility that = (Possibility) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "Possibility(" +
                "data=" + Arrays.toString(data) +
                ')';
    }
}
