package duc.sg.java.loadapproximator.test.utils;

import duc.sg.java.loadapproximator.utils.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTests {


    @Test
    public void test() {
        var matrix = new Matrix(2,3);
        Assertions.assertArrayEquals(new double[]{0,0,0,0,0,0}, matrix.getData());

        matrix.set(0,0, 1);
        matrix.set(0,1, 2);
        matrix.set(0,2, 3);
        matrix.set(1,0, 4);
        matrix.set(1,1, 5);
        matrix.set(1,2, 6);

        Assertions.assertArrayEquals(new double[]{1,2,3,4,5,6}, matrix.getData());

        matrix.addLine();
        var expected1 = new double[]{
                1,2,3,
                4,5,6,
                0,0,0
        };
        Assertions.assertArrayEquals(expected1, matrix.getData());

        matrix.addColumn();
        var expected2 = new double[]{
                1,2,3,0,
                4,5,6,0,
                0,0,0,0
        };
        Assertions.assertArrayEquals(expected2, matrix.getData());

    }

    @Test
    public void testAddManyColumns() {
        var matrix = new Matrix(2,3);
        matrix.set(0,0, 1);
        matrix.set(0,1, 2);
        matrix.set(0,2, 3);
        matrix.set(1,0, 4);
        matrix.set(1,1, 5);
        matrix.set(1,2, 6);

        matrix.addColumns(3);

        var expected1 = new double[]{
                1,2,3,0,0,0,
                4,5,6,0,0,0,
        };
        Assertions.assertArrayEquals(expected1, matrix.getData());
    }

    @Test
    public void testAddManyLines() {
        var matrix = new Matrix(2,3);
        matrix.set(0,0, 1);
        matrix.set(0,1, 2);
        matrix.set(0,2, 3);
        matrix.set(1,0, 4);
        matrix.set(1,1, 5);
        matrix.set(1,2, 6);

        matrix.addLines(3);

        var expected1 = new double[]{
                1,2,3,
                4,5,6,
                0,0,0,
                0,0,0,
                0,0,0,
        };
        Assertions.assertArrayEquals(expected1, matrix.getData());
    }

}
