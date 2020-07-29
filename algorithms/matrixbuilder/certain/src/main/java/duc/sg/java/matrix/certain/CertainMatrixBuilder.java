package duc.sg.java.matrix.certain;

import duc.sg.java.circlefinder.Circle;
import duc.sg.java.circlefinder.CircleFinder;
import duc.sg.java.circlefinder.CircleUtils;
import duc.sg.java.matrix.FuseStateMatrix;
import duc.sg.java.matrix.MatrixBuilder;
import duc.sg.java.model.*;
import duc.sg.java.navigation.Actionner;
import duc.sg.java.navigation.Condition;
import duc.sg.java.navigation.bfs.BFSEntity;
import duc.sg.java.utils.MatrixDouble;

import java.util.*;

public class CertainMatrixBuilder implements MatrixBuilder {
    private Map<Fuse, Integer> idxFuses;
    private int[] idxLast;
    private MatrixDouble equations;
    private Set<Circle> processedCircle;
    private List<Cable> mapLineFuse;

    private int getOrCreateIdx(Fuse fuse, Map<Fuse, Integer> map, int[] last) {
        map.computeIfAbsent(fuse, keyFuse -> ++last[0]);
        return map.get(fuse);
    }

    private void init() {
        idxFuses = new HashMap<>();
        idxLast = new int[]{-1};
        equations = new MatrixDouble();
        processedCircle = new HashSet<>();
        mapLineFuse = new ArrayList<>();
    }

    @Override
    public FuseStateMatrix[] build(Substation substation, Configuration configuration) {
        init();

        CircleFinder.getDefault().getCircles(substation);

        Actionner<Entity> actionner = (Entity currEntity) -> {
            final List<Fuse> closedFuses = configuration.getClosedFuses(currEntity);

            if (!(currEntity instanceof Substation) && closedFuses.size() > 1) {
                equations.addLine();
                mapLineFuse.add(null);
            }
            int rowCabEq = equations.getNumRows() - 1;

            for (Fuse fuse : closedFuses) {
                cableEq(configuration, fuse);
                cabinetEq(currEntity, closedFuses, rowCabEq, fuse);
                circleEq(substation, configuration, fuse);
            }
        };

        Condition condition = (Fuse currFuse) -> {
            Fuse oppFuse = currFuse.getOpposite();
            Entity oppEntity = oppFuse.getOwner();
            return configuration.isClosed(currFuse) &&
                    configuration.isClosed(oppFuse) &&
                    !configuration.isDeadEnd(oppEntity);
        };

        BFSEntity.INSTANCE.navigate(substation, actionner, condition);

        var resData = (equations.getData().length == 0)? new double[]{0} : equations.getData();
        FuseStateMatrix res = new CertainFuseStateMatrix(resData, equations.getNumCols(), idxFuses, mapLineFuse.toArray(new Cable[0]));
        return new FuseStateMatrix[]{res};
    }

    private void circleEq(Substation substation, Configuration configuration, Fuse fuse) {
        List<Circle> circles = CircleUtils.circlesWith(substation, fuse);
        circles.forEach((Circle circle) -> {
            if(circle.isEffective(configuration) && !processedCircle.contains(circle)) {
                processedCircle.add(circle);
                Fuse end = circle.getOtherEndPoint(fuse);
                addCircleEq(fuse, end);
            }
        });
    }

    private void cabinetEq(Entity currEntity, List<Fuse> fuses, int rowCabEq, Fuse fuse) {
        if (!(currEntity instanceof Substation) && fuses.size() > 1) {
            int idxFuse = getOrCreateIdx(fuse, idxFuses, idxLast);
            equations.set( rowCabEq, idxFuse, 1);
        }
    }

    private void cableEq(Configuration configuration, Fuse fuse) {
        if (!idxFuses.containsKey(fuse)) {
            addCableEq(configuration, fuse);
        }
    }

    private void addCircleEq(Fuse fuse, Fuse fuseEnd) {
        int idxFuse = getOrCreateIdx(fuse, idxFuses, idxLast);
        int idxFuseEnd = getOrCreateIdx(fuseEnd, idxFuses, idxLast);

        equations.addLine();
        mapLineFuse.add(null);
        equations.set(equations.getNumRows() - 1, idxFuse, 1);
        equations.set(equations.getNumRows() - 1, idxFuseEnd, -1);
    }

    private void addCableEq(Configuration configuration, Fuse fuse) {
        Fuse oppFuse = fuse.getOpposite();
        equations.addLine();
        mapLineFuse.add(fuse.getCable());
        int idxFuse = getOrCreateIdx(fuse, idxFuses, idxLast);
        equations.set(equations.getNumRows() - 1, idxFuse, 1);

        if (configuration.isClosed(oppFuse) && !configuration.isDeadEnd(oppFuse.getOwner())) {
            int idxOpp = getOrCreateIdx(oppFuse, idxFuses, idxLast);
            equations.set( equations.getNumRows() - 1, idxOpp, 1);
        }
    }

}
