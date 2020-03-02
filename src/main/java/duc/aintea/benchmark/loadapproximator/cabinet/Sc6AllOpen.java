package duc.aintea.benchmark.loadapproximator.cabinet;

public class Sc6AllOpen extends CabinetBench {
    @Override
    protected void openFuses() {
        for (var f: fuses) {
            f.openFuse();
        }
    }
}
