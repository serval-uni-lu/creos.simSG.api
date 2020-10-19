package creos.simsg.api.loadapproximator.uncertain.multisubs.brainstorm;

import creos.simsg.api.circlefinder.Circle;
import creos.simsg.api.circlefinder.CircleFinder;
import creos.simsg.api.model.Cabinet;
import creos.simsg.api.model.Entity;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;
import creos.simgsg.api.utils.StringAccumlator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to represent the path between two fuses. A path is the set of fuses and cabinets through which the power flow can go.
 * Moreover, it stores the number of cut it requires to break the power flow, and thus the path, between the two
 * fuses.
 */
public class Path {
    private final Fuse startFuse, endFuse;
    private final Set<Fuse> fuses;
    private int nbCut;

    public Path(Fuse start, Fuse end, Set<Fuse> fuses) {
        this.startFuse = start;
        this.endFuse = end;
        this.fuses = fuses;
    }

    public boolean exists() {
        return !fuses.isEmpty();
    }

    public Set<Entity> getAllEntities() {
       return getFuses().stream()
               .map(Fuse::getOwner)
               .collect(Collectors.toSet());
    }

    public Set<Cabinet> getCabinets() {
        return fuses.stream()
                .map(Fuse::getOwner)
                .map(entity -> (Cabinet) entity)
                .collect(Collectors.toSet());
    }

    public void setNbCut(int nbCutForPath) {
        this.nbCut = nbCutForPath;
    }

    public int getNbCut() {
        return nbCut;
    }

    public Collection<Fuse> getFuses() {
       var allFuses = new HashSet<>(fuses);
       Collections.addAll(allFuses, startFuse, endFuse);
       return allFuses;
    }

    public List<Circle> getCircles() {
        var subStart = (Substation) startFuse.getOwner();
        return CircleFinder.getDefault().getCircles(subStart);
    }

    @Override
    public String toString() {
        String cabNames = getCabinets().stream()
                .map(Cabinet::getName)
                .reduce("", StringAccumlator.INSTANCE);

        String fuseNames = getFuses().stream()
                .map(Fuse::getName)
                .reduce("", StringAccumlator.INSTANCE);

        return "Path(start: " + startFuse.getOwner().getName() + "[" + startFuse.getName() + "], " +
                "end: " + endFuse.getOwner().getName() + "[" + endFuse.getName() + "], " +
                "path: [" + cabNames + "], " +
                "path_fuses: [" + fuseNames + "], " +
                "nbCut: " + nbCut + ")";

    }
}
