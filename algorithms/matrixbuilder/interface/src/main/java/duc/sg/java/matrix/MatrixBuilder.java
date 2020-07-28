package duc.sg.java.matrix;

import duc.sg.java.model.Configuration;
import duc.sg.java.model.Substation;

public interface MatrixBuilder {

    FuseStateMatrix[] build(Substation substation, Configuration configuration);
    default FuseStateMatrix[] build(Substation substation) {
        return build(substation, MatrixBuilderUtils.extractEffectiveConfiguration(substation));
    }
}
