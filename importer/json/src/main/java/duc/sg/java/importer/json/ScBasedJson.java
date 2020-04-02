package duc.sg.java.importer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jackson.JsonLoader;
import duc.sg.java.model.Fuse;
import duc.sg.java.model.State;
import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class ScBasedJson {
    private static final String SC_ID_KEY = "scenario";
    private static final String UC_FUSES_KEY = "uncertainFuses";
    private static final String OPEN_FUSES_KEY = "openFuses";
    private static final String CONSUMPTIONS_KEY = "consumptions";

    private static final String UC_ID_KEY = "id";
    private static final String UC_STATUS_KEY = "status";
    private static final String UC_CONF_LVL_KEY = "confidenceLevel";

    private ScBasedJson(){}

    private static boolean validate(JsonNode toCheck) throws ValidationException {
        if(!SchemaValidator.validate(toCheck,"scenario-schema.json")) {
            return false;
        }

        int scId = toCheck.get(SC_ID_KEY).asInt();
        ArrayNode consumptionNode = (ArrayNode) toCheck.get(CONSUMPTIONS_KEY);
        ArrayNode openFuses = (ArrayNode) toCheck.get(OPEN_FUSES_KEY);
        ArrayNode uncertainFuses = (ArrayNode) toCheck.get(UC_FUSES_KEY);

        int nbFuses =  ScenarioName.idToName(scId).getNbFuses();
        if(nbFuses == ScenarioName.UNDEFINED.getNbFuses()) {
            return false;
        }

        if(consumptionNode.size() != (nbFuses/2) ||
                (openFuses != null && openFuses.size() > nbFuses) ||
                (uncertainFuses != null && uncertainFuses.size() > nbFuses)) {
            return false;
        }

        if(uncertainFuses != null) {
            Iterator<JsonNode> itUcNodes = uncertainFuses.elements();
            while (itUcNodes.hasNext()) {
                JsonNode ucNode = itUcNodes.next();
                double confLvl = ucNode.get(UC_CONF_LVL_KEY).asDouble();
                if(confLvl < 0 || confLvl > 1) {
                    return false;
                }

                int id = ucNode.get(UC_ID_KEY).asInt();
                if(id < 1 || id > nbFuses) {
                    return false;
                }
            }
        }

        return true;

    }

    private static State[] getFuseStates(int nbFuses, JsonNode toImport) {
        ArrayNode openFusesVal = (ArrayNode) toImport.get(OPEN_FUSES_KEY);
        final var fuseStates = new State[nbFuses];
        Arrays.fill(fuseStates, State.CLOSED);
        openFusesVal.elements().forEachRemaining((JsonNode idxNode) -> fuseStates[idxNode.asInt() - 1] = State.OPEN);
        return fuseStates;
    }

    private static double[] getConsumptions(int nbCables, JsonNode toImport) {
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

    private static void makesUncertain(Fuse[] fuses, JsonNode toImport) {
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

    public static Optional<Scenario> from(String jsonText) throws ValidationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromString(jsonText);
        } catch (IOException e) {
            throw new ValidationException(e);
        }

        return extractSubstation(toImport);
    }


    public static Optional<Scenario> from(File jsonFile) throws ValidationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromFile(jsonFile);
        } catch (IOException e) {
            throw new ValidationException(e);
        }

        return extractSubstation(toImport);
    }

    private static Optional<Scenario> extractSubstation(JsonNode toImport) throws ValidationException {
        if(validate(toImport)) {
            ScenarioName name;
            int nbFuses;
            switch(toImport.get(SC_ID_KEY).asInt()) {
                case 1: {
                    name = ScenarioName.SINGLE_CABLE;
                    nbFuses = 2;
//                    boolean[] fuseClosed = getFuseStates(2, toImport);
//                    double[] consumptions = getConsumptions(1, toImport);
//                    substation = SingleCableBuilder.build(fuseClosed, consumptions);
//                    fuses = SingleCableBuilder.extractFuses(substation);
                    break;
                }
                case 2: {
                    name = ScenarioName.CABINET;
                    nbFuses = 6;
//                    boolean[] fuseClosed = getFuseStates(6, toImport);
//                    double[] consumptions = getConsumptions(3, toImport);
//                    substation = CabinetBuilder.build(fuseClosed, consumptions);
//                    fuses = CabinetBuilder.extractFuses(substation);
                    break;
                }
                case 3: {
                    name = ScenarioName.PARA_TRANSFORMER;
                    nbFuses = 6;
//                    boolean[] fuseClosed = getFuseStates(6, toImport);
//                    double[] consumptions = getConsumptions(3, toImport);
//                    substation = ParaTransformerBuilder.build(fuseClosed, consumptions);
//                    fuses = ParaTransformerBuilder.extractFuses(substation);
                    break;
                }
                case 4: {
                    name = ScenarioName.PARA_CABINET;
                    nbFuses = 8;
//                    boolean[] fuseClosed = getFuseStates(8, toImport);
//                    double[] consumptions = getConsumptions(4, toImport);
//                    substation = ParaCabinetBuilder.build(fuseClosed, consumptions);
//                    fuses = ParaCabinetBuilder.extractFuses(substation);
                    break;
                }
                case 5: {
                    name = ScenarioName.INDIRECT_PARALLEL;
                    nbFuses = 10;
//                    boolean[] fuseClosed = getFuseStates(10, toImport);
//                    double[] consumptions = getConsumptions(5, toImport);
//                    substation = IndirectParaBuilder.build(fuseClosed, consumptions);
//                    fuses = IndirectParaBuilder.extractFuses(substation);
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

}