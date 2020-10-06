package duc.sg.java.server.message;

import duc.sg.java.loadapproximator.certain.CertainApproximator;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;

import java.util.ArrayList;

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
            CertainApproximator.INSTANCE
                    .getFuseLoads(substation, true)
                    .forEach((Fuse fuse, Double load) -> {
                        fuseLoads.add(new LoadApproximationAnswer.Load(fuse.getId(), load));
                    });

            CertainApproximator.INSTANCE
                    .getCableLoads(substation)
                    .forEach((Cable cable, Double load) -> {
                        cableLoads.add(new LoadApproximationAnswer.Load(cable.getId(), load));
                    });
        });

        return new LoadApproximationAnswer(
                fuseLoads.toArray(new LoadApproximationAnswer.Load[0]),
                cableLoads.toArray(new LoadApproximationAnswer.Load[0])
        );
    }
}
