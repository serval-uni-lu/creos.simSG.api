package duc.sg.java.server.message;

import duc.sg.java.extracter.CableExtracter;
import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.loadapproximator.certain.CertainApproximator;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;

import java.util.ArrayList;
import java.util.Map;

public class LoadApproxMsg extends Message implements RequestMessage {
    private SmartGrid grid;

    LoadApproxMsg(SmartGrid grid) {
        super(MessageType.LOAD_APPROX);
        this.grid = grid;
    }

    @Override
    public Message process() {
        final var fuseLoads = new ArrayList<LoadApproximationAnswer.Load>();
        final var cableLoads = new ArrayList<LoadApproximationAnswer.Load>();
        grid.getSubstations().forEach((Substation substation) -> {
            Map<Fuse, Double> fLoads = CertainApproximator.INSTANCE
                    .getFuseLoads(substation, true);

            FuseExtracter.INSTANCE
                    .getExtracted(substation)
                    .forEach((Fuse fuse) -> {
                        fuseLoads.add(new LoadApproximationAnswer.Load(
                                fuse.getId(),
                                fLoads.getOrDefault(fuse, 0.)
                        ));
                    });


            Map<Cable, Double> cLoads = CertainApproximator.INSTANCE
                    .getCableLoads(substation);

            CableExtracter.INSTANCE
                    .getExtracted(substation)
                    .forEach((Cable cable) -> {
                        cableLoads.add(new LoadApproximationAnswer.Load(
                                cable.getId(),
                                cLoads.getOrDefault(cable, 0.)
                        ));
                    });
        });

        return new LoadApproximationAnswer(
                fuseLoads.toArray(new LoadApproximationAnswer.Load[0]),
                cableLoads.toArray(new LoadApproximationAnswer.Load[0])
        );
    }
}
