package duc.aintea.tests;

import org.ejml.data.DenseMatrix64F;

public class Runner {


    public static void main(String[] args) {
//        var matrix = new double[] {
//                1,1,0,0,
//                0,0,1,0,
//                0,0,0,1,
//                0,1,1,1,
//        };
//
//        var approximator = new LoadApproximation(new double[]{10,20,30,0}, matrix);
//        System.out.println(Arrays.toString(approximator.computeLoad()));

        var matrix = new DenseMatrix64F(2,2, true, 1,2,3,4);

        System.out.println(matrix);

        matrix.reshape(matrix.numRows, matrix.numCols - 1, true);
        System.out.println(matrix);

    }
}
