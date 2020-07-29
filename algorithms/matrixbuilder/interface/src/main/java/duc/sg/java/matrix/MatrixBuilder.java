package duc.sg.java.matrix;

import duc.sg.java.model.Configuration;
import duc.sg.java.model.Substation;

public interface MatrixBuilder {

    EquationMatrix[] build(Substation substation, Configuration configuration);
    default EquationMatrix[] build(Substation substation) {
        return build(substation, MatrixBuilderUtils.extractEffectiveConfiguration(substation));
    }
}
