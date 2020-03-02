package duc.aintea.benchmark.loadapproximator.paraCabinet;

public class Sc18F3F4Open extends ParaCabinetBench {
    @Override
    protected void openFuses() {
        fuses[2].openFuse();
        fuses[3].openFuse();
    }
}
