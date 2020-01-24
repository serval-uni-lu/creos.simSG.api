package duc.aintea.tests.loadapproximation;

public class Scenario5 extends LoadApproximation {
    private static final double[] FUSE_STATES = {   1,1,0,0,0,0,0,0,0,0,
                                                    0,0,1,1,0,0,0,0,0,0,
                                                    0,0,0,0,1,1,0,0,0,0,
                                                    0,0,0,0,0,0,1,1,0,0,
                                                    0,0,0,0,0,0,0,0,1,1,
                                                    0,0,0,1,1,0,1,0,0,0,
                                                    0,1,0,0,0,0,0,1,1,0,
                                                    0,0,0,0,0,1,0,0,0,0,
                                                    0,0,0,0,0,0,0,0,0,1,
                                                    1,0,-1,0,0,0,0,0,0,0};



    public Scenario5(double iL1, double iL2, double iL3, double il4, double il5) {
        super(new double[]{iL1, iL2, iL3, il4, il5, 0, 0, 0, 0, 0}, FUSE_STATES);
    }
}
