package duc.aintea.tests.loadapproximation;

public class Scenario2 extends LoadApproximation {
    private static final double[] FUSE_STATES = {   1,1,0,0,0,0,
                                                    0,0,1,0,1,0,
                                                    0,0,0,1,0,1,
                                                    0,1,1,1,0,0,
                                                    0,0,0,0,1,0,
                                                    0,0,0,0,0,1};

    public Scenario2(double iL1, double iL2, double iL3) {
        super(new double[]{iL1, iL2, iL3, 0, 0, 0}, FUSE_STATES);
    }
}
