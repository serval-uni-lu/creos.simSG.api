package creos.simsg.api.matrix;

import creos.simsg.api.extractor.EffectiveConfigurationExtractor;
import creos.simsg.api.model.Configuration;
import creos.simsg.api.model.Substation;

/**
 * Interface that should be implemented by all algorithms for creating the equation matrix.
 */
public interface MatrixBuilder {

    EquationMatrix[] build(Substation substation, Configuration configuration);
    default EquationMatrix[] build(Substation substation) {
        return build(
                substation,
                EffectiveConfigurationExtractor.INSTANCE
                        .getExtracted(substation)
                        .get(0)
        );
    }
}
