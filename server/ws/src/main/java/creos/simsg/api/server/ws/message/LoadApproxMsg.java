package creos.simsg.api.server.ws.message;

import creos.simsg.api.extractor.CableExtractor;
import creos.simsg.api.extractor.FuseExtractor;
import creos.simsg.api.loadapproximator.certain.CertainApproximator;
import creos.simsg.api.model.Cable;
import creos.simsg.api.model.Fuse;
import creos.simsg.api.model.SmartGrid;
import creos.simsg.api.model.Substation;

import java.util.ArrayList;
import java.util.Map;

/**
 * This message is sent by client to request a load approximation
 */
public class LoadApproxMsg extends Message implements RequestMessage {
    private final SmartGrid grid;

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

            FuseExtractor.INSTANCE
                    .getExtracted(substation)
                    .forEach((Fuse fuse) -> {
                        fuseLoads.add(new LoadApproximationAnswer.Load(
                                fuse.getId(),
                                fLoads.getOrDefault(fuse, 0.)
                        ));
                    });


            Map<Cable, Double> cLoads = CertainApproximator.INSTANCE
                    .getCableLoads(substation);

            CableExtractor.INSTANCE
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
