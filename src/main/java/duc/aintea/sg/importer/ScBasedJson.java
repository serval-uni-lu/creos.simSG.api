package duc.aintea.sg.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jackson.JsonLoader;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.*;

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
        if(!SchemaValidator.validate(toCheck,"./scenario-schema.json")) {
            return false;
        }

        int scId = toCheck.get(SC_ID_KEY).asInt();
        ArrayNode consumptionNode = (ArrayNode) toCheck.get(CONSUMPTIONS_KEY);
        ArrayNode openFuses = (ArrayNode) toCheck.get(OPEN_FUSES_KEY);
        ArrayNode uncertainFuses = (ArrayNode) toCheck.get(UC_FUSES_KEY);

        int nbFuses;
        if(scId == 1) {
            nbFuses = 2;
        } else if(scId == 2 || scId == 3) {
           nbFuses = 6;
        } else if(scId == 4) {
            nbFuses = 8;
        } else if(scId == 5) {
            nbFuses = 10;
        } else {
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

    private static boolean[] getFuseStates(int nbFuses, JsonNode toImport) {
        JsonNode openFusesVal = toImport.get(OPEN_FUSES_KEY);
        final var fuseStates = new boolean[nbFuses];
        Arrays.fill(fuseStates, true);
        openFusesVal.elements().forEachRemaining((JsonNode idxNode) -> fuseStates[idxNode.asInt() - 1] = false);
        return fuseStates;
    }

    private static double[] getConsumptions(int nbCables, JsonNode toImport) {
        final double[] consumptions = new double[nbCables];
        JsonNode consumptionsNode = toImport.get(CONSUMPTIONS_KEY);
        Iterator<JsonNode> vals = consumptionsNode.elements();
        int idx = 0;
        while (vals.hasNext() && idx<nbCables) {
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
            if(status.equalsIgnoreCase("OPEN")) {
                fuse.openFuse();
            }
            var conf = nodeUc.get(UC_CONF_LVL_KEY).asDouble();
            fuse.getStatus().setConfAsProb(conf);
        });
    }

    public static Optional<Substation> from(String jsonText) throws ValidationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromString(jsonText);
        } catch (IOException e) {
            throw new ValidationException(e);
        }

        return extractSubstation(toImport);
    }


    public static Optional<Substation> from(File jsonFile) throws ValidationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromFile(jsonFile);
        } catch (IOException e) {
            throw new ValidationException(e);
        }

        return extractSubstation(toImport);
    }

    private static Optional<Substation> extractSubstation(JsonNode toImport) throws ValidationException {
        if(validate(toImport)) {
            Substation substation;
            Fuse[] fuses;
            switch(toImport.get(SC_ID_KEY).asInt()) {
                case 1: {
                    boolean[] fuseClosed = getFuseStates(2, toImport);
                    double[] consumptions = getConsumptions(1, toImport);
                    substation = SingleCableBuilder.build(fuseClosed, consumptions);
                    fuses = SingleCableBuilder.extractFuses(substation);
                    break;
                }
                case 2: {
                    boolean[] fuseClosed = getFuseStates(6, toImport);
                    double[] consumptions = getConsumptions(3, toImport);
                    substation = CabinetBuilder.build(fuseClosed, consumptions);
                    fuses = CabinetBuilder.extractFuses(substation);
                    break;
                }
                case 3: {
                    boolean[] fuseClosed = getFuseStates(6, toImport);
                    double[] consumptions = getConsumptions(3, toImport);
                    substation = ParaTransformerBuilder.build(fuseClosed, consumptions);
                    fuses = ParaTransformerBuilder.extractFuses(substation);
                    break;
                }
                case 4: {
                    boolean[] fuseClosed = getFuseStates(8, toImport);
                    double[] consumptions = getConsumptions(4, toImport);
                    substation = ParaCabinetBuilder.build(fuseClosed, consumptions);
                    fuses = ParaCabinetBuilder.extractFuses(substation);
                    break;
                }
                case 5: {
                    boolean[] fuseClosed = getFuseStates(10, toImport);
                    double[] consumptions = getConsumptions(5, toImport);
                    substation = IndirectParaBuilder.build(fuseClosed, consumptions);
                    fuses = IndirectParaBuilder.extractFuses(substation);
                    break;
                }
                default: return Optional.empty();
            }
            makesUncertain(fuses, toImport);
            return Optional.of(substation);
        } else {
            return Optional.empty();
        }
    }

}
