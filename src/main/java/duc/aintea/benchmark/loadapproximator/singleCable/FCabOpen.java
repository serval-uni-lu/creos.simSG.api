package duc.aintea.benchmark.loadapproximator.singleCable;

public class FCabOpen extends SingleCableBench {
    @Override
    protected void openFuses() {
        fuses[1].openFuse();
    }
}
