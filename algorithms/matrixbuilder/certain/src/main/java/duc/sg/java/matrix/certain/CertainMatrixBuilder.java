package duc.sg.java.matrix.certain;

import duc.sg.java.circlefinder.Circle;
import duc.sg.java.circlefinder.CircleFinder;
import duc.sg.java.circlefinder.CircleUtils;
import duc.sg.java.matrix.FuseStateMatrix;
import duc.sg.java.matrix.MatrixBuilder;
import duc.sg.java.model.*;
import duc.sg.java.utils.MatrixDouble;

import java.util.*;

public class CertainMatrixBuilder implements MatrixBuilder {
    public static final MatrixBuilder INSTANCE = new CertainMatrixBuilder();

    private CertainMatrixBuilder(){}


    @Override
    public FuseStateMatrix[] build(Substation substation, Map<Fuse, State> configuration) {
        CircleFinder.getDefault().findAndSave(substation);

        final var idxFuses = new HashMap<Fuse, Integer>();
        var idxLast = new int[]{-1};

        final var cableEq = new MatrixDouble();
        final var cabinetEq = new MatrixDouble();
        final var circleEq = new MatrixDouble();

        var waitingList = new ArrayDeque<Entity>();
        var entityVisited = new HashSet<Entity>();
        var fuseInCircles = new HashSet<Fuse>();
        var paraCableFusesDone = new HashSet<>();

        var mapLineFuse = new ArrayList<Cable>();

        waitingList.add(substation);

        while(!waitingList.isEmpty()) {
            var currEntity = waitingList.remove();
            var notYetVisited = entityVisited.add(currEntity);

            if(notYetVisited) {
                final List<Fuse> fuses = currEntity.getClosedFuses();

                if (!(currEntity instanceof Substation) && fuses.size() > 1) {
                    cabinetEq.addLine();
                }

                for (Fuse fuse : fuses) {
                    if (!idxFuses.containsKey(fuse)) {
                        Fuse oppFuse = fuse.getOpposite();
                        cableEq.addLine();
                        mapLineFuse.add(fuse.getCable());
                        int idxFuse = Utils.getOrCreateIdx(fuse, idxFuses, idxLast);
                        cableEq.set(cableEq.getNumRows() - 1, idxFuse, 1);

                        if (oppFuse.isClosed() && !oppFuse.getOwner().isDeadEnd()) {
                            int idxOpp = Utils.getOrCreateIdx(oppFuse, idxFuses, idxLast);
                            cableEq.set( cableEq.getNumRows() - 1, idxOpp, 1);
                            if (!entityVisited.contains(oppFuse.getOwner())) {
                                waitingList.add(oppFuse.getOwner());
                            }
                        }
                    }

                    if (!(currEntity instanceof Substation) && fuses.size() > 1) {
                        int idxFuse = Utils.getOrCreateIdx(fuse, idxFuses, idxLast);
                        cabinetEq.set( cabinetEq.getNumRows() - 1, idxFuse, 1);
                    }

                    if(!fuseInCircles.contains(fuse)) {
                        Optional<Circle> optCircle = CircleUtils.circleFrom(substation, fuse);
                        if(optCircle.isPresent() && (optCircle.get().isValid())) {
                            Circle circle = optCircle.get();
                            Collections.addAll(fuseInCircles, circle.getFuses());

                            Fuse fuseEnd = circle.getOtherEndPoint(fuse);
                            paraCableFusesDone.add(fuse);
                            paraCableFusesDone.add(fuseEnd);
                            int idxFuse = Utils.getOrCreateIdx(fuse, idxFuses, idxLast);
                            int idxFuseEnd = Utils.getOrCreateIdx(fuseEnd, idxFuses, idxLast);

                            circleEq.addLine();
                            circleEq.set(circleEq.getNumRows() - 1, idxFuse, 1);
                            circleEq.set(circleEq.getNumRows() - 1, idxFuseEnd, -1);
                        }
                    } else if(!entityVisited.contains(fuse.getOpposite().getOwner()) &&
                            !paraCableFusesDone.contains(fuse)) {
                        Optional<Fuse> oppFuse = Utils.getOtherFusePara(fuse, fuses);
                        if(oppFuse.isPresent()) {
                            Fuse fuseEnd = oppFuse.get();
                            paraCableFusesDone.add(fuse);
                            paraCableFusesDone.add(fuseEnd);

                            int idxFuse = Utils.getOrCreateIdx(fuse, idxFuses, idxLast);
                            int idxFuseEnd = Utils.getOrCreateIdx(fuseEnd, idxFuses, idxLast);

                            circleEq.addLine();
                            circleEq.set( circleEq.getNumRows() - 1, idxFuse, 1);
                            circleEq.set( circleEq.getNumRows() - 1, idxFuseEnd, -1);
                        }

                    }
                }
            }
        }

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
