package duc.aintea.tests.loadapproximation;

public class Scenario1 extends LoadApproximation {
    private static final double[] FUSE_STATES = {1, 1, 0, 1};

    public Scenario1(double il1) {
        super(new double[]{il1, 0}, FUSE_STATES);
    }
}
