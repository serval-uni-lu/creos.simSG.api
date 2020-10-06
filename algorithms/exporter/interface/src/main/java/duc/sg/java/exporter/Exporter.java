package duc.sg.java.exporter;

import duc.sg.java.loadapproximator.LoadApproximator;
import duc.sg.java.model.SmartGrid;

public interface Exporter<E> {
    E export(SmartGrid grid);
    E exportWithLoads(SmartGrid grid, LoadApproximator<Double> approximator);
}
