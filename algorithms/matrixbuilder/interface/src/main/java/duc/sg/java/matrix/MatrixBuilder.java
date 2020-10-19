package duc.sg.java.matrix;

import duc.sg.java.extractor.EffectiveConfigurationExtractor;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Substation;

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
