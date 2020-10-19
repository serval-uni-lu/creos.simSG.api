package creos.simsg.api.preprocessor.powerflow;

import creos.simsg.api.circlefinder.Circle;
import creos.simsg.api.circlefinder.CircleFinder;
import creos.simsg.api.extractor.FuseExtractor;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.Substation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Algorithm to retrieve the mandatory power flow: all fuses that do not belong to a circle.
 */
public class PowerFlow2 implements IPowerFlow {
    @Override
    public Fuse[] getFuseOnMandatoryPF(Substation substation) {
        List<Circle> circles = CircleFinder.getDefault().getCircles(substation);
        List<Fuse> allFuses = FuseExtractor.INSTANCE.getExtracted(substation);

        var fuseOnCircles = new HashSet<Fuse>();
        circles.forEach((Circle c) -> Collections.addAll(fuseOnCircles, c.getFuses()));

        return allFuses.stream()
                .filter((Fuse f) -> !fuseOnCircles.contains(f) && !f.getOwner().isAlwaysDeadEnd())
                .toArray(Fuse[]::new);

    }
}
