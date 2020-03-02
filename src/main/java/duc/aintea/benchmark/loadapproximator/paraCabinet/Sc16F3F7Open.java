package duc.aintea.benchmark.loadapproximator.paraCabinet;

public class Sc16F3F7Open extends ParaCabinetBench {
    @Override
    protected void openFuses() {
        fuses[2].openFuse();
        fuses[6].openFuse();
    }
}
