package creos.simsg.api.transformer;

import creos.simsg.api.loadapproximator.LoadApproximator;
import creos.simsg.api.model.SmartGrid;

/**
 * Interface for any class that transform the grid to another model
 * @param <E>
 */
public interface Exporter<E> {
    E export(SmartGrid grid);
    E exportWithLoads(SmartGrid grid, LoadApproximator<Double> approximator);
}
