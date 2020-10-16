package duc.sg.java.loadapproximator.uncertain.naive;

import java.util.Arrays;

/**
 * A class that shows that uncertainty propagation through operators, as intended while working on the
 * <a href="https://github.com/lmouline/aintea">Ain'tea</a> language, cannot work for load approximation.
 */
public class ProofSimplePropagationCannotWork {

    // Working scenario:
    //   |-[f1]--------[f2]-|
    // s-<                   >-c
    //   |-[f3]--------[f4]-|
    // f2 status is uncertain


    static class M {
        // cas 1: f2 is closed
//        double[] matrix = new double[]{
//                1,1,0,0,
//                0,0,1,1,
//                1,0,-1,0,
//                0,1,0,1
//        };

        // cas 2: f2 is open
        double[] matrix = new double[]{
                1,1,0,0,
                0,0,1,1,
                0,1,0,0,
                0,0,0,1
        };

        double get(int l, int c) {
            var ll = l-1;
            var cc = c - 1;
            return matrix[ll*4 + cc];
        }
    }




    public static void main(String[] args) {
        var m = new M();

          // global equation to compute the determinant
//        var determinant =
//                +(m.get(1,1)*m.get(2,2)*m.get(3,3)*m.get(4,4)) + (m.get(1,1)*m.get(2,3)*m.get(3,4)*m.get(4,2)) + (m.get(1,1)*m.get(2,4)*m.get(3,2)*m.get(4,3))
//                -(m.get(1,1)*m.get(2,4)*m.get(3,3)*m.get(4,2)) - (m.get(1,1)*m.get(2,3)*m.get(3,2)*m.get(4,4)) - (m.get(1,1)*m.get(2,2)*m.get(3,4)*m.get(4,3))
//                -(m.get(1,2)*m.get(2,1)*m.get(3,3)*m.get(4,4)) - (m.get(1,3)*m.get(2,1)*m.get(3,4)*m.get(4,2)) - (m.get(1,4)*m.get(2,1)*m.get(3,2)*m.get(4,3))
//                +(m.get(1,4)*m.get(2,1)*m.get(3,3)*m.get(4,2)) + (m.get(1,3)*m.get(2,1)*m.get(3,2)*m.get(4,4)) + (m.get(1,2)*m.get(2,1)*m.get(3,4)*m.get(4,3))
//                +(m.get(1,2)*m.get(2,3)*m.get(3,1)*m.get(4,4)) + (m.get(1,3)*m.get(2,4)*m.get(3,1)*m.get(4,2)) + (m.get(1,4)*m.get(2,2)*m.get(3,1)*m.get(4,3))
//                -(m.get(1,4)*m.get(2,3)*m.get(3,1)*m.get(4,2)) - (m.get(1,3)*m.get(2,2)*m.get(3,1)*m.get(4,4)) - (m.get(1,2)*m.get(2,4)*m.get(3,1)*m.get(4,3))
//                -(m.get(1,2)*m.get(2,3)*m.get(3,4)*m.get(4,1)) - (m.get(1,3)*m.get(2,4)*m.get(3,2)*m.get(4,1)) - (m.get(1,4)*m.get(2,2)*m.get(3,3)*m.get(4,1))
//                +(m.get(1,4)*m.get(2,3)*m.get(3,2)*m.get(4,1)) + (m.get(1,3)*m.get(2,2)*m.get(3,4)*m.get(4,1)) + (m.get(1,2)*m.get(2,4)*m.get(3,3)*m.get(4,1));


        // part of the equation that is not impacted by the uncertainty of f2 status
        var c_determinant =
                + (m.get(1,1)*m.get(2,4)*m.get(3,2)*m.get(4,3))
                - (m.get(1,1)*m.get(2,2)*m.get(3,4)*m.get(4,3))
                - (m.get(1,4)*m.get(2,1)*m.get(3,2)*m.get(4,3))
                + (m.get(1,2)*m.get(2,1)*m.get(3,4)*m.get(4,3))
                - (m.get(1,2)*m.get(2,3)*m.get(3,4)*m.get(4,1))
                + (m.get(1,3)*m.get(2,2)*m.get(3,4)*m.get(4,1));

        // part of the equation that is impacted by the uncertainty of f2 status
        var u_determinant = new double[]{                                                        // cas 1     cas 2
                +(m.get(1, 1) * m.get(2, 2) * m.get(3, 3) * m.get(4, 4)), //  0          0
                -(m.get(1, 1) * m.get(2, 4) * m.get(3, 3) * m.get(4, 2)), //  1          0
                -(m.get(1, 1) * m.get(2, 3) * m.get(3, 2) * m.get(4, 4)), //  0          -1
                -(m.get(1, 2) * m.get(2, 1) * m.get(3, 3) * m.get(4, 4)), //  0          0
                +(m.get(1, 4) * m.get(2, 1) * m.get(3, 3) * m.get(4, 2)), //  0          0
                +(m.get(1, 3) * m.get(2, 1) * m.get(3, 2) * m.get(4, 4)), //  0          0
                +(m.get(1, 2) * m.get(2, 3) * m.get(3, 1) * m.get(4, 4)), //  1          0
                +(m.get(1, 3) * m.get(2, 4) * m.get(3, 1) * m.get(4, 2)), //  0          0
                +(m.get(1, 4) * m.get(2, 2) * m.get(3, 1) * m.get(4, 3)), //  0          0
                -(m.get(1, 4) * m.get(2, 3) * m.get(3, 1) * m.get(4, 2)), //  0          0
                -(m.get(1, 3) * m.get(2, 2) * m.get(3, 1) * m.get(4, 4)), //  0          0
                -(m.get(1, 2) * m.get(2, 4) * m.get(3, 1) * m.get(4, 3)), //  0          0
                +(m.get(1, 4) * m.get(2, 3) * m.get(3, 2) * m.get(4, 1)), //  0          0
                +(m.get(1, 1) * m.get(2, 3) * m.get(3, 4) * m.get(4, 2)), //  0          0
                -(m.get(1, 3) * m.get(2, 1) * m.get(3, 4) * m.get(4, 2)), //  0          0
                -(m.get(1, 3) * m.get(2, 4) * m.get(3, 2) * m.get(4, 1)), //  0          0
                -(m.get(1, 4) * m.get(2, 2) * m.get(3, 3) * m.get(4, 1)), //  0          0
                +(m.get(1, 2) * m.get(2, 4) * m.get(3, 3) * m.get(4, 1))  //  0          0
        };
        var sum = 0;
        for (var d: u_determinant) sum+= d;

        // if uncertainty is propagated at the operator level, then the sum will result in 4 different values: {-1, 0, 1, 2}
        // and so we will get 4 different solutions for the determinant: {-1, 0, 1, 2}
        // whereas we should get two possibilities: {-1, 2}
        // uncertainty should thus be propagated at a higher "level"


        var global = c_determinant + sum;

        System.out.println(global);
        System.out.println(c_determinant);
        System.out.println(Arrays.toString(u_determinant));









    }
}
