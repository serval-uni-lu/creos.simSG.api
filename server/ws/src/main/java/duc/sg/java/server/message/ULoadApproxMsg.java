package duc.sg.java.server.message;

import duc.sg.java.extracter.CableExtracter;
import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.loadapproximator.uncertain.naive.UncertainLoadApproximator;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;
import duc.sg.java.uncertainty.PossibilityDouble;

import java.util.ArrayList;

public class ULoadApproxMsg extends Message implements RequestMessage {
    final SmartGrid grid;

    ULoadApproxMsg(SmartGrid grid) {
        super(MessageType.ULOAD_APPROX);

        this.grid = grid;
    }

    @Override
    public Message process() {
        final var fuseLoads = new ArrayList<ULoadApproximationAnswer.ElmtULoad>();
        final var cableLoads = new ArrayList<ULoadApproximationAnswer.ElmtULoad>();

        grid.getSubstations().forEach((Substation substation) -> {
            UncertainLoadApproximator.approximate(substation);
            FuseExtracter.INSTANCE
                    .getExtracted(substation)
                    .forEach((Fuse fuse) -> {
                        var jsonULoads = new ArrayList<ULoadApproximationAnswer.ULoad>();
                        fuse.getUncertainLoad().forEach((PossibilityDouble poss) -> {
                            jsonULoads.add(new ULoadApproximationAnswer.ULoad(poss.getValue(), poss.getConfidence().getProbability()));
                        });

                        fuseLoads.add(new ULoadApproximationAnswer.ElmtULoad(
                              fuse.getId(),
                              jsonULoads.toArray(new ULoadApproximationAnswer.ULoad[0])
                        ));
                    });

            CableExtracter.INSTANCE
                    .getExtracted(substation)
                    .forEach((Cable cable) -> {
                        var jsonULoads = new ArrayList<ULoadApproximationAnswer.ULoad>();
                        cable.getUncertainLoad().forEach((PossibilityDouble poss) -> {
                            jsonULoads.add(new ULoadApproximationAnswer.ULoad(poss.getValue(), poss.getConfidence().getProbability()));
                        });

                        cableLoads.add(new ULoadApproximationAnswer.ElmtULoad(
                                cable.getId(),
                                jsonULoads.toArray(new ULoadApproximationAnswer.ULoad[0])
                        ));
                    });
        });

        return new ULoadApproximationAnswer(
                fuseLoads.toArray(new ULoadApproximationAnswer.ElmtULoad[0]),
                cableLoads.toArray(new ULoadApproximationAnswer.ElmtULoad[0])
        );
    }
}
