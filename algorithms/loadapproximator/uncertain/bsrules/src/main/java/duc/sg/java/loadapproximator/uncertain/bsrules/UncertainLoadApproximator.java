package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.cycle.all.InitAllCycleSubs2;
import duc.sg.java.grid.uncertainty.configuration.Configuration;
import duc.sg.java.grid.uncertainty.configuration.ConfigurationMatrix;
import duc.sg.java.grid.uncertainty.configuration.EmptyConfigurationMatrix;
import duc.sg.java.matrix.certain.MatrixBuilder;
import duc.sg.java.matrix.uncertain.UncertainFuseStatesMatrix;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.model.Substation;
import duc.sg.java.uncertainty.PossibilityDouble;
import duc.sg.java.utils.BaseTransform;
import duc.sg.java.utils.Pair;
import duc.sg.java.validator.rules.CircleRule;
import org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
import org.ejml.data.DenseMatrix64F;

import java.util.*;

public class UncertainLoadApproximator {
    private UncertainLoadApproximator() {}

    private static List<Fuse> getUncertainFuses(Fuse[] fuses) {
        var uFuses = new ArrayList<Fuse>();
        for (Fuse f: fuses) {
            if(!f.getOwner().isDeadEnd() && f.getStatus().isUncertain()) {
                uFuses.add(f);
            }
        }

        return uFuses;
    }


    private static Map<Fuse, State> boolArr2MapFuseState(boolean[] fuseStates, List<Fuse> uFuses, Fuse[] allFuses) {
        var res = new HashMap<Fuse, State>(allFuses.length);

        for(Fuse f: allFuses) {
            State state;
            if(uFuses.contains(f)) {
                int idx = uFuses.indexOf(f);
                state = fuseStates[idx]? State.CLOSED : State.OPEN;
            } else {
                state = f.getStatus().isClosed()? State.CLOSED : State.OPEN;
            }
            res.put(f, state);
        }

        return res;
    }

    public static ConfigurationMatrix getAllConfigurations(Substation substation) {
        UFuseDetector.detectAndModifyUFuses(substation);


        InitAllCycleSubs2.init(substation);
        substation.updateAllFuses();
        Collection<Fuse[]> allCycles = substation.getCycles();

        if(allCycles.isEmpty()) {
            return new EmptyConfigurationMatrix();
        }

        var gridConf = new ConfigurationMatrix();

        CircleRule circleRule = new CircleRule();

        for(Fuse[] cycle: allCycles) {
            List<Fuse> uFuses = getUncertainFuses(cycle);
            int nbPossibilities = (int) Math.pow(2, uFuses.size());

            if(nbPossibilities == 1) {
                continue; //ignore this circle
            }

            var circleConf = new ConfigurationMatrix(uFuses.toArray(new Fuse[0]));
            double confToAdd = 0;




            for (int idxConf = 0; idxConf < nbPossibilities; idxConf++) {
                boolean[] fuseStates = BaseTransform.toBinary(idxConf, uFuses.size());
                Map<Fuse, State> fuseStateMap = boolArr2MapFuseState(fuseStates, uFuses, cycle);

                double confidence = 1;

                for(Map.Entry<Fuse, State> fuseState: fuseStateMap.entrySet()) {
                    if(fuseState.getValue() == State.CLOSED) {
                        confidence *= fuseState.getKey().getStatus().confIsClosed();
                    } else {
                        confidence *= fuseState.getKey().getStatus().confIsOpen();
                    }
                }

                if(circleRule.apply(cycle, fuseStateMap)) {
                    circleConf.add(fuseStateMap, confidence);
                } else {
                    confToAdd += confidence;
                }

            }

            if(confToAdd > 0) {
                circleConf.addConfToMaxOpen(confToAdd);
            }

            gridConf.add(circleConf);
            

        }

        if(gridConf.getConfidences().isEmpty()) {
            return new EmptyConfigurationMatrix();
        }

        return gridConf;

    }

    public static UncertainFuseStatesMatrix[] build(Substation substation) {
        ConfigurationMatrix gridConfigurations = getAllConfigurations(substation);

        var fuseMatrices = new UncertainFuseStatesMatrix[gridConfigurations.nbConfigurations()];

        int idx = 0;
        for(Configuration configuration: gridConfigurations) {
            for (Iterator<Pair<Fuse, State>> it = configuration.getFuseStates(); it.hasNext(); ) {
                Pair<Fuse, State> fuseState = it.next();
                if(fuseState.getSecond() == State.CLOSED) {
                    fuseState.getFirst().closeFuse();
                } else {
                    fuseState.getFirst().openFuse();
                }
            }

            fuseMatrices[idx] = new UncertainFuseStatesMatrix(
                    MatrixBuilder.build(substation),
                    configuration.getConfidence()
            );

            idx++;
        }

        return fuseMatrices;
    }




    public static void approximate(final Substation substation) {
        UncertainFuseStatesMatrix[] matrices = build(substation);
        var visited = new HashSet<Fuse>();

        substation.updateAllFuses();

         for (UncertainFuseStatesMatrix usfm : matrices) {
            var fuseStates = new DenseMatrix64F(usfm.getNbColumns(), usfm.getNbColumns(), true, usfm.getData());

            final var matConsumptions = new DenseMatrix64F(usfm.getNbColumns(), 1);
            var cblesOrder = usfm.getCables();
            for (int i = 0; i < cblesOrder.length; i++) {
                matConsumptions.set(i, 0, cblesOrder[i].getConsumption());
            }


            DenseMatrix64F solution = new DenseMatrix64F(matConsumptions.numRows, matConsumptions.numCols);
            SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
            solver.setA(fuseStates);

            solver.solve(matConsumptions, solution);

            var solData = solution.data;
            var fuses = new HashSet<Fuse>(substation.getAllFuses());
            for (int i = 0; i < solData.length; i++) {
                Fuse current = usfm.getFuse(i);
                if (!visited.contains(current)) {
                    current.resetULoad();
                    visited.add(current);
                }

                var newPoss = new PossibilityDouble(solData[i], usfm.getConfidence());
                current.getUncertainLoad().addPossibility(newPoss);
                fuses.remove(current);
            }

            for(var f: fuses) {
                if (!visited.contains(f)) {
                    f.resetULoad();
                    visited.add(f);
                }
                var newPoss = new PossibilityDouble(0, usfm.getConfidence());
                f.getUncertainLoad().addPossibility(newPoss);
            }
        }

    }

}
