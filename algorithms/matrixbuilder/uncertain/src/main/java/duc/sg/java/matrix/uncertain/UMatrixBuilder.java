package duc.sg.java.matrix.uncertain;

import duc.sg.java.extractor.EffectiveConfigurationExtractor;
import duc.sg.java.extractor.UFuseExtractor;
import duc.sg.java.matrix.certain.EquationMatrixImp;
import duc.sg.java.matrix.certain.CertainMatrixBuilder;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.utils.BaseTransform;

import java.util.ArrayList;
import java.util.List;

public class UMatrixBuilder {
    private UMatrixBuilder(){}

    public static List<UEquationMatrix> build(Substation substation) {
        List<Fuse> uFuses = UFuseExtractor.INSTANCE.getExtracted(substation);
        var nbPossibilities = Math.pow(2, uFuses.size());

        var res = new ArrayList<UEquationMatrix>((int) nbPossibilities);
        for (int idxCase = 0; idxCase < nbPossibilities; idxCase++) {
            boolean[] fuseStates = BaseTransform.toBinary(idxCase, uFuses.size());
            double confidence = 1;

            Configuration configuration = EffectiveConfigurationExtractor.INSTANCE
                    .getExtracted(substation)
                    .get(0);

            for (int idxFuse = 0; idxFuse < uFuses.size(); idxFuse++) {
                if(fuseStates[idxFuse]){
                    configuration.set(uFuses.get(idxFuse), State.CLOSED);
                    confidence *= uFuses.get(idxFuse).getStatus().confIsClosed();
                } else {
                    configuration.set(uFuses.get(idxFuse), State.OPEN);
                    confidence *= uFuses.get(idxFuse).getStatus().confIsOpen();
                }
            }

            EquationMatrixImp matrix = (EquationMatrixImp) new CertainMatrixBuilder().build(substation, configuration)[0];
            res.add(new UEquationMatrix(matrix, confidence));
        }

        return res;
    }
}
