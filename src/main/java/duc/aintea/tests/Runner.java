package duc.aintea.tests;

import duc.aintea.tests.loadapproximation.Scenario1;

import java.util.Arrays;

public class Runner {


    public static void main(String[] args) {
        Scenario1 sc1 = new Scenario1(10);
        System.out.println(Arrays.toString(sc1.computeLoad()));
    }
}
