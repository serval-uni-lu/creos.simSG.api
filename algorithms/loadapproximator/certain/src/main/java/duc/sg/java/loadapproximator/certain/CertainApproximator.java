package duc.sg.java.loadapproximator.certain;

import duc.sg.java.extracter.CableExtracter;
import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.loadapproximator.LoadApproximator;
import duc.sg.java.matrix.FuseStateMatrix;
import duc.sg.java.matrix.certain.CertainMatrixBuilder;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Configuration;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.Substation;
import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CertainApproximator implements LoadApproximator<Double> {
    public static final CertainApproximator INSTANCE = new CertainApproximator();

    private CertainApproximator(){}

    @Override
    public void approximateAndSave(Substation substation, Configuration configuration) {
        FuseStateMatrix matrix = new CertainMatrixBuilder().build(substation)[0];

        var fuseStates = new DenseMatrix64F(matrix.getNbColumns(), matrix.getNbColumns(), true, matrix.getData());

        final var matConsumptions = new DenseMatrix64F(matrix.getNbColumns(), 1);
        Cable[] cblesOrder = matrix.getCables();
        for (int i = 0; i < cblesOrder.length; i++) {
            if (cblesOrder[i] != null) {
                matConsumptions.set(i,0, cblesOrder[i].getConsumption());
            }
        }


        DenseMatrix64F solution = new DenseMatrix64F(matConsumptions.numRows, matConsumptions.numCols);
        SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
        solver.setA(fuseStates);

        solver.solve(matConsumptions, solution);

        var solData = solution.data;
        var res = new HashMap<Fuse, Double>();
        FuseExtracter.INSTANCE
                .getExtracted(substation)
                .forEach((Fuse f) -> {
                    Integer idx = matrix.getFuseIdx(f);
                    if(solData.length == 0 || idx == null) {
                        res.put(f,0.);
                    } else {
                        res.put(f, solData[matrix.getFuseIdx(f)]);
                    }
                });

        substation.getGrid()
                .save(KeyComputer.getKey(substation), res);
    }

    @Override
    public Map<Fuse, Double> getFuseLoads(Substation substation, Configuration configuration) {
        String key = KeyComputer.getKey(substation);
        Optional<Object> optFuseLoads = substation.getGrid().retrieve(key);
        if(optFuseLoads.isEmpty()) {
            approximateAndSave(substation, configuration);
            optFuseLoads = substation.getGrid().retrieve(key);
        }

        return (Map<Fuse, Double>) optFuseLoads.get();
    }

    @Override
    public Map<Cable, Double> getCableLoads(Substation substation, Configuration configuration) {
        Map<Fuse, Double> fuseLoads = getFuseLoads(substation, configuration);
        List<Cable> allCables = CableExtracter.INSTANCE.getExtracted(substation);

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
