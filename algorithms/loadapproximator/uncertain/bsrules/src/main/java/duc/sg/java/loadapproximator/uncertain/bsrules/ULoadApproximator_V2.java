package duc.sg.java.loadapproximator.uncertain.bsrules;

import duc.sg.java.circlefinder.Circle;
import duc.sg.java.circlefinder.CircleFinder;
import duc.sg.java.extractor.FuseExtractor;
import duc.sg.java.grid.uncertainty.configuration.UConfiguration;
import duc.sg.java.grid.uncertainty.configuration.UConfigurationList;
import duc.sg.java.grid.uncertainty.configuration.EmptyUConfigurationList;
import duc.sg.java.matrix.certain.EquationMatrixImp;
import duc.sg.java.matrix.certain.CertainMatrixBuilder;
import duc.sg.java.matrix.uncertain.UEquationMatrix;
import duc.sg.java.model.Configuration;
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

/**
 * Second version of the uncertain load approximation with business rules.
 *
 * In this version, the listing of all valid configurations is done by first listing all valid configurations of
 * circles, with their confidence levels. Then, we use the cartesian product to combine all of them and retrieve
 * the list of all valid configurations for the topology.
 *
 * TODO 1: this class should implement {@link duc.sg.java.loadapproximator.LoadApproximator}. A new class should be
 *  defined to type uncertain load
 *
 * TODO 2: Extract the matrix creation into another maven module (to put in the
 *   <em>algorithms/matrixbuilder</em> folder).
 */
public class ULoadApproximator_V2 {
    private ULoadApproximator_V2() {}

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

    public static UConfigurationList getAllConfigurations(Substation substation) {
        UFuseDetector.detectAndModifyUFuses(substation);
        List<Circle> allCycles = CircleFinder.getDefault().getCircles(substation);

        if(allCycles.isEmpty()) {
            return new EmptyUConfigurationList();
        }

        var gridConf = new UConfigurationList();

        CircleRule circleRule = new CircleRule();

        for(Circle circle: allCycles) {
            List<Fuse> uFuses = getUncertainFuses(circle.getFuses());
            int nbPossibilities = (int) Math.pow(2, uFuses.size());

            if(nbPossibilities == 1) {
                continue; //ignore this circle
            }

            var circleConf = new UConfigurationList(uFuses.toArray(new Fuse[0]));
            double confToAdd = 0;




            for (int idxConf = 0; idxConf < nbPossibilities; idxConf++) {
                boolean[] fuseStates = BaseTransform.toBinary(idxConf, uFuses.size());
                Map<Fuse, State> fuseStateMap = boolArr2MapFuseState(fuseStates, uFuses, circle.getFuses());

                double confidence = 1;

                for(Map.Entry<Fuse, State> fuseState: fuseStateMap.entrySet()) {
                    if(fuseState.getValue() == State.CLOSED) {
                        confidence *= fuseState.getKey().getStatus().confIsClosed();
                    } else {
                        confidence *= fuseState.getKey().getStatus().confIsOpen();
                    }
                }

                if(circleRule.apply(circle.getFuses(), fuseStateMap)) {
                    circleConf.add(new Configuration(fuseStateMap), confidence);
                } else {
                    confToAdd += confidence;
                }

            }

            if(confToAdd > 0) {
                circleConf.addConfToMaxClosed(confToAdd);
            }

            gridConf.add(circleConf);
            

        }

        if(gridConf.getConfidences().isEmpty()) {
            return new EmptyUConfigurationList();
        }

        return gridConf;

    }

    public static UEquationMatrix[] build(Substation substation) {
        UConfigurationList gridConfigurations = getAllConfigurations(substation);

        var fuseMatrices = new UEquationMatrix[gridConfigurations.nbConfigurations()];

        int idx = 0;
        for(UConfiguration UConfiguration : gridConfigurations) {
            for (Iterator<Pair<Fuse, State>> it = UConfiguration.getFuseStates(); it.hasNext(); ) {
                Pair<Fuse, State> fuseState = it.next();
                if(fuseState.getSecond() == State.CLOSED) {
                    fuseState.getFirst().closeFuse();
                } else {
                    fuseState.getFirst().openFuse();
                }
            }

            fuseMatrices[idx] = new UEquationMatrix(
                    (EquationMatrixImp) new CertainMatrixBuilder().build(substation)[0],
                    UConfiguration.getConfidence()
            );

            idx++;
        }

        return fuseMatrices;
    }




    public static void approximate(final Substation substation) {
        UEquationMatrix[] matrices = build(substation);
        var visited = new HashSet<Fuse>();

         for (UEquationMatrix usfm : matrices) {
            var fuseStates = new DenseMatrix64F(usfm.getNbColumns(), usfm.getNbColumns(), true, usfm.getValues());

            final var matConsumptions = new DenseMatrix64F(usfm.getNbColumns(), 1, true, usfm.getEqResults());


            DenseMatrix64F solution = new DenseMatrix64F(matConsumptions.numRows, matConsumptions.numCols);
            SolvePseudoInverseSvd solver = new SolvePseudoInverseSvd();
            solver.setA(fuseStates);

            solver.solve(matConsumptions, solution);

            var solData = solution.data;
            var fuses = new HashSet<Fuse>(FuseExtractor.INSTANCE.getExtracted(substation));
            for (int i = 0; i < solData.length; i++) {
                Fuse current = usfm.getColumn(i);
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