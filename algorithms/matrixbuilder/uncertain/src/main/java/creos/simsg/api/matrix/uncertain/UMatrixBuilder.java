package creos.simsg.api.matrix.uncertain;

import creos.simsg.api.extractor.EffectiveConfigurationExtractor;
import creos.simsg.api.extractor.UFuseExtractor;
import creos.simsg.api.matrix.certain.EquationMatrixImp;
import creos.simsg.api.matrix.certain.CertainMatrixBuilder;
import creos.simsg.api.model.Configuration;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.State;
import creos.simsg.api.model.Substation;
import creos.simgsg.api.utils.BaseTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Build the uncertain equation matrices. This is done by computing the equation matrix for each possible equations.
 */
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
