package duc.sg.java.importer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import duc.sg.java.importer.ImportationException;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class JsonSCImporter extends JsonImporter<Scenario> {
    static final String SC_ID_KEY = "scenario";
    static final String UC_FUSES_KEY = "uncertainFuses";
    static final String OPEN_FUSES_KEY = "openFuses";
    static final String CONSUMPTIONS_KEY = "consumptions";

    static final String UC_ID_KEY = "id";
    static final String UC_STATUS_KEY = "status";
    static final String UC_CONF_LVL_KEY = "confidenceLevel";

    public static final JsonSCImporter INSTANCE = new JsonSCImporter();

    private JsonSCImporter(){}

    @Override
    protected Optional<Scenario> extract(JsonNode toImport) throws ImportationException {
        if(JsonScValidator.validate(toImport)) {
            ScenarioName name;
            int nbFuses;
            switch(toImport.get(SC_ID_KEY).asInt()) {
                case 1: {
                    name = ScenarioName.SINGLE_CABLE;
                    nbFuses = 2;
                    break;
                }
                case 2: {
                    name = ScenarioName.CABINET;
                    nbFuses = 6;
                    break;
                }
                case 3: {
                    name = ScenarioName.PARA_TRANSFORMER;
                    nbFuses = 6;
                    break;
                }
                case 4: {
                    name = ScenarioName.PARA_CABINET;
                    nbFuses = 8;
                    break;
                }
                case 5: {
                    name = ScenarioName.INDIRECT_PARALLEL;
                    nbFuses = 10;
                    break;
                }
                default: return Optional.empty();
            }

            State[] fuseClosed = getFuseStates(nbFuses, toImport);
            double[] consumptions = getConsumptions(nbFuses/2, toImport);
            Scenario sc = new ScenarioBuilder()
                    .chooseScenario(name)
                    .setConsumptions(consumptions)
                    .setFuseStates(fuseClosed)
                    .build();
            makesUncertain(sc.extractFuses(), toImport);
            return Optional.of(sc);
        } else {
            return Optional.empty();
        }
    }

    private State[] getFuseStates(int nbFuses, JsonNode toImport) {
        ArrayNode openFusesVal = (ArrayNode) toImport.get(OPEN_FUSES_KEY);
        final var fuseStates = new State[nbFuses];
        Arrays.fill(fuseStates, State.CLOSED);
        openFusesVal.elements().forEachRemaining((JsonNode idxNode) -> fuseStates[idxNode.asInt() - 1] = State.OPEN);
        return fuseStates;
    }

    private double[] getConsumptions(int nbCables, JsonNode toImport) {
        ArrayNode consumptionsNode = (ArrayNode) toImport.get(CONSUMPTIONS_KEY);
        final double[] consumptions = new double[nbCables];
        Iterator<JsonNode> vals = consumptionsNode.elements();
        int idx = 0;
        while (vals.hasNext() && idx<consumptions.length) {
            consumptions[idx] = vals.next().asDouble();
            idx++;
        }
        return consumptions;
    }

    private void makesUncertain(Fuse[] fuses, JsonNode toImport) {
        JsonNode uncertainFuses = toImport.get(UC_FUSES_KEY);

        uncertainFuses.forEach((JsonNode nodeUc) -> {
            int idx = nodeUc.get(UC_ID_KEY).asInt() -1;
            String status = nodeUc.get(UC_STATUS_KEY).asText();

            var fuse = fuses[idx];
            var conf = nodeUc.get(UC_CONF_LVL_KEY).asDouble();
            if(status.equalsIgnoreCase("OPEN")) {
                fuse.openFuse();
                fuse.getStatus().setConfIsOpen(conf);
            } else {
                fuse.getStatus().setConfIsClosed(conf);
            }
        });
    }


}
