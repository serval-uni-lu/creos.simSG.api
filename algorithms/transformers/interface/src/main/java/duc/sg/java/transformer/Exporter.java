package duc.sg.java.transformer;

import duc.sg.java.loadapproximator.LoadApproximator;
import duc.sg.java.model.SmartGrid;

/**
 * Interface for any class that transform the grid to another model
 * @param <E>
 */
public interface Exporter<E> {
    E export(SmartGrid grid);
    E exportWithLoads(SmartGrid grid, LoadApproximator<Double> approximator);
}
