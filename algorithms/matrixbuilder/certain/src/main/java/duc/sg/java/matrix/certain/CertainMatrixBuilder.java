package duc.sg.java.matrix.certain;

import duc.sg.java.circlefinder.Circle;
import duc.sg.java.circlefinder.CircleFinder;
import duc.sg.java.circlefinder.CircleUtils;
import duc.sg.java.matrix.FuseStateMatrix;
import duc.sg.java.matrix.MatrixBuilder;
import duc.sg.java.model.*;
import duc.sg.java.navigation.Actionner;
import duc.sg.java.navigation.bfs.BFSEntity;
import duc.sg.java.navigation.bfs.Condition;
import duc.sg.java.utils.MatrixDouble;

import java.util.*;

public class CertainMatrixBuilder implements MatrixBuilder {
    public static final MatrixBuilder INSTANCE = new CertainMatrixBuilder();

    private CertainMatrixBuilder(){}

    @Override
    public FuseStateMatrix[] build(Substation substation, Configuration configuration) {
        CircleFinder.getDefault().findAndSave(substation);

        final var idxFuses = new HashMap<Fuse, Integer>();
        var idxLast = new int[]{-1};

        final var cableEq = new MatrixDouble();
        final var cabinetEq = new MatrixDouble();
        final var circleEq = new MatrixDouble();

        var fuseInCircles = new HashSet<Fuse>();
        var paraCableFusesDone = new HashSet<>();

        var mapLineFuse = new ArrayList<Cable>();

        Actionner<Entity> actionner = (Entity currEntity, Set<Entity> visited) -> {
            final List<Fuse> fuses = configuration.getClosedFuses(currEntity);


            if (!(currEntity instanceof Substation) && fuses.size() > 1) {
                cabinetEq.addLine();
            }

            for (Fuse fuse : fuses) {
                if (!idxFuses.containsKey(fuse)) {
                    addCableEq(configuration, idxFuses, idxLast, cableEq, mapLineFuse, fuse);
                }

                if (!(currEntity instanceof Substation) && fuses.size() > 1) {
                    int idxFuse = Utils.getOrCreateIdx(fuse, idxFuses, idxLast);
                    cabinetEq.set( cabinetEq.getNumRows() - 1, idxFuse, 1);
                }



                if(!fuseInCircles.contains(fuse)) {
                    Optional<Circle> optCircle = CircleUtils.circleFrom(substation, fuse);
                    if(optCircle.isPresent() && (optCircle.get().isEffective(configuration))) {
                        Circle circle = optCircle.get();
                        Collections.addAll(fuseInCircles, circle.getFuses());

                        Fuse fuseEnd = circle.getOtherEndPoint(fuse);
                        addCircleEq(idxFuses, idxLast, circleEq, paraCableFusesDone, fuse, fuseEnd);
                    }
                } else if(!visited.contains(fuse.getOpposite().getOwner()) && !paraCableFusesDone.contains(fuse)) {
                    Optional<Fuse> oppFuse = Utils.getOtherFusePara(fuse, fuses);
                    if(oppFuse.isPresent()) {
                        Fuse fuseEnd = oppFuse.get();
                        addCircleEq(idxFuses, idxLast, circleEq, paraCableFusesDone, fuse, fuseEnd);
                    }

                }
            }
        };

        Condition<Fuse, Entity> condition = (Fuse currFuse, Set<Entity> visited) -> {
            Fuse oppFuse = currFuse.getOpposite();
            Entity oppEntity = oppFuse.getOwner();
            return configuration.isClosed(currFuse) &&
                    configuration.isClosed(oppFuse) &&
                    !configuration.isDeadEnd(oppEntity) &&
                    !visited.contains(oppEntity);
        };

        BFSEntity.INSTANCE.navigate(substation, actionner, condition);

        return buildMatrix(idxFuses, cableEq, cabinetEq, circleEq, mapLineFuse);
    }

    private void addCircleEq(HashMap<Fuse, Integer> idxFuses, int[] idxLast, MatrixDouble circleEq, HashSet<Object> paraCableFusesDone, Fuse fuse, Fuse fuseEnd) {
        paraCableFusesDone.add(fuse);
        paraCableFusesDone.add(fuseEnd);
        int idxFuse = Utils.getOrCreateIdx(fuse, idxFuses, idxLast);
        int idxFuseEnd = Utils.getOrCreateIdx(fuseEnd, idxFuses, idxLast);

        circleEq.addLine();
        circleEq.set(circleEq.getNumRows() - 1, idxFuse, 1);
        circleEq.set(circleEq.getNumRows() - 1, idxFuseEnd, -1);
    }

    private void addCableEq(Configuration configuration, HashMap<Fuse, Integer> idxFuses, int[] idxLast, MatrixDouble cableEq, ArrayList<Cable> mapLineFuse, Fuse fuse) {
        Fuse oppFuse = fuse.getOpposite();
        cableEq.addLine();
        mapLineFuse.add(fuse.getCable());
        int idxFuse = Utils.getOrCreateIdx(fuse, idxFuses, idxLast);
        cableEq.set(cableEq.getNumRows() - 1, idxFuse, 1);

        if (configuration.isClosed(oppFuse) && !configuration.isDeadEnd(oppFuse.getOwner())) {
            int idxOpp = Utils.getOrCreateIdx(oppFuse, idxFuses, idxLast);
            cableEq.set( cableEq.getNumRows() - 1, idxOpp, 1);
        }
    }

    private static FuseStateMatrix[] buildMatrix(HashMap<Fuse, Integer> idxFuses, MatrixDouble cableEq, MatrixDouble cabinetEq, MatrixDouble circleEq, ArrayList<Cable> mapLineFuse) {
        if(circleEq.getNumCols() < cableEq.getNumCols()) {
            circleEq.addColumns(cableEq.getNumCols() - circleEq.getNumCols());
        }

        var resData = new double[cableEq.getData().length + cabinetEq.getData().length + circleEq.getData().length];
        System.arraycopy(cableEq.getData(), 0, resData, 0, cableEq.getData().length);
        System.arraycopy(cabinetEq.getData(), 0, resData, cableEq.getData().length, cabinetEq.getData().length);
        System.arraycopy(circleEq.getData(), 0, resData, cableEq.getData().length + cabinetEq.getData().length, circleEq.getData().length);
        resData = (resData.length == 0)? new double[]{0} : resData;

        FuseStateMatrix res = new CertainFuseStateMatrix(resData, cableEq.getNumCols(), idxFuses, mapLineFuse.toArray(new Cable[0]));

        return new FuseStateMatrix[]{res};
    }


}
