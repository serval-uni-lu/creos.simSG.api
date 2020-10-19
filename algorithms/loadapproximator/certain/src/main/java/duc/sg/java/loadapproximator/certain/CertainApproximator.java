package duc.sg.java.loadapproximator.certain;

import duc.sg.java.extractor.CableExtractor;
import duc.sg.java.extractor.FuseExtractor;
import duc.sg.java.loadapproximator.LoadApproximator;
import duc.sg.java.matrix.EquationMatrix;
import duc.sg.java.matrix.certain.CertainMatrixBuilder;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import duc.sg.java.validator.GridValidator;
import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Approximate the load by solving the matrix equation: B = C / A,
 * where A is a matrix of 0/1 that represents equations of the grid,
 * C a vector with all consumptions and B a vector with fuse loads.
 *
 * In the code: B is named "solution",  A is named "fuseStates", and B "matConsumptions"
 *
 * Cable loads equal the maximal load in its fuses.
 *
 */
public class CertainApproximator implements LoadApproximator<Double> {
    public static final CertainApproximator INSTANCE = new CertainApproximator();

    private CertainApproximator(){}

    @Override
    public Map<Fuse, Double> approximate(Substation substation, Configuration configuration) {
        var validator = new GridValidator();

        if(validator.isValid(substation, configuration.getConfiguration())){
            EquationMatrix equationMatrix = new CertainMatrixBuilder().build(substation)[0];

            var fuseStates = new DenseMatrix64F(equationMatrix.getNbColumns(), equationMatrix.getNbColumns(), true, equationMatrix.getValues());
            final var matConsumptions = new DenseMatrix64F(equationMatrix.getNbColumns(), 1, true, equationMatrix.getEqResults());

            DenseMatrix64F solution = new DenseMatrix64F(matConsumptions.numRows, matConsumptions.numCols);
            SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
            solver.setA(fuseStates);

            solver.solve(matConsumptions, solution);

            var solData = solution.data;
            var res = new HashMap<Fuse, Double>();
            FuseExtractor.INSTANCE
                    .getExtracted(substation)
                    .forEach((Fuse f) -> {
                        Integer idx = equationMatrix.getColumnIdx(f);
                        if(solData.length == 0 || idx == null) {
                            res.put(f,0.);
                        } else {
                            res.put(f, solData[equationMatrix.getColumnIdx(f)]);
                        }
                    });
            return res;
        }
        return new HashMap<>();

    }

    @Override
    public void approximateAndSave(Substation substation, Configuration configuration) {
        substation.getGrid()
                .save(KeyComputer.getKey(substation), approximate(substation, configuration));
    }

    @Override
    public Map<Fuse, Double> getFuseLoads(Substation substation, Configuration configuration, boolean forceRecompute) {
        String key = KeyComputer.getKey(substation);
        Optional<Object> optFuseLoads = substation.getGrid().retrieve(key);
        if(forceRecompute || optFuseLoads.isEmpty()) {
            approximateAndSave(substation, configuration);
            optFuseLoads = substation.getGrid().retrieve(key);
        }

        return (Map<Fuse, Double>) optFuseLoads.get();
    }

    @Override
    public Map<Cable, Double> getCableLoads(Substation substation, Configuration configuration, boolean forceRecompute) {
        Map<Fuse, Double> fuseLoads = getFuseLoads(substation, configuration, forceRecompute);
        List<Cable> allCables = CableExtractor.INSTANCE.getExtracted(substation);

        var cableLoad = new HashMap<Cable, Double>(allCables.size());
        for(Cable cable: allCables) {
            Double loadF1 = fuseLoads.get(cable.getFirstFuse());
            Double loadF2 = fuseLoads.get(cable.getSecondFuse());
            if(loadF1 == null && loadF2 == null) {
                cableLoad.put(cable, -1.);
            } else if(loadF1 == null) {
                cableLoad.put(cable, loadF2);
            } else if(loadF2 == null) {
                cableLoad.put(cable, loadF1);
            } else {
                cableLoad.put(cable, Math.max(loadF1, loadF2));
            }
        }


        return cableLoad;
    }
}
