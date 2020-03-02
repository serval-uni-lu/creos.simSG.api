package duc.aintea.benchmark.loadapproximator.paraCabinet;

public class Sc10F5F7Open extends ParaCabinetBench {
    @Override
    protected void openFuses() {
        fuses[4].openFuse();
        fuses[6].openFuse();
    }
}
