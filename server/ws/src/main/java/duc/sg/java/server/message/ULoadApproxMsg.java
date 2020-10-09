package duc.sg.java.server.message;

import duc.sg.java.extracter.CableExtracter;
import duc.sg.java.extracter.FuseExtracter;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.SmartGrid;
import duc.sg.java.model.Substation;
import duc.sg.java.uncertainty.PossibilityDouble;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ULoadApproxMsg extends Message implements RequestMessage {
    final SmartGrid grid;
    final ULoadType type;

    ULoadApproxMsg(SmartGrid grid, ULoadType type) {
        super(MessageType.ULOAD_APPROX);
        this.type = type;
        this.grid = grid;
    }

    @Override
    public Message process() {
        final var fuseLoads = new ArrayList<ULoadApproximationAnswer.ElmtULoad>();
        final var cableLoads = new ArrayList<ULoadApproximationAnswer.ElmtULoad>();

        switch (type) {
            case NAIVE ->
                grid.getSubstations().forEach(
                        duc.sg.java.loadapproximator.uncertain.naive.UncertainLoadApproximator::approximate
                );
            case BS_RULE ->
                grid.getSubstations().forEach(
                        duc.sg.java.loadapproximator.uncertain.bsrules.UncertainLoadApproximator::approximate
                );
        }

        final var consumer = new ULoadConsumer(fuseLoads, cableLoads);
        grid.getSubstations().forEach(consumer);

        return new ULoadApproximationAnswer(
                fuseLoads.toArray(new ULoadApproximationAnswer.ElmtULoad[0]),
                cableLoads.toArray(new ULoadApproximationAnswer.ElmtULoad[0])
        );
    }

    private static class ULoadConsumer implements Consumer<Substation> {
        final ArrayList<ULoadApproximationAnswer.ElmtULoad> fuseLoads;
        final ArrayList<ULoadApproximationAnswer.ElmtULoad> cableLoads;

        private ULoadConsumer(ArrayList<ULoadApproximationAnswer.ElmtULoad> fuseLoads, ArrayList<ULoadApproximationAnswer.ElmtULoad> cableLoads) {
            this.fuseLoads = fuseLoads;
            this.cableLoads = cableLoads;
        }

        @Override
        public void accept(Substation substation) {
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
        }
    }


    public enum ULoadType {
        NAIVE, BS_RULE
    }
}
