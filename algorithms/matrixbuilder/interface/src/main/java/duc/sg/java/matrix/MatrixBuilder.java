package duc.sg.java.matrix;

import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;

import java.util.Map;

public interface MatrixBuilder {

    FuseStateMatrix[] build(Substation substation, Map<Fuse, State> configuration);
    default FuseStateMatrix[] build(Substation substation) {
        return build(substation, MatrixBuilderUtils.extractEffectiveConfiguration(substation));
    }
}
