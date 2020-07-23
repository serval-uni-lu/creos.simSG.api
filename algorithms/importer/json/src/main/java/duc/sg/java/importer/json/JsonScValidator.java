package duc.sg.java.importer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import duc.sg.java.importer.ImportationException;
import duc.sg.java.scenarios.ScenarioName;

import java.util.Iterator;

class JsonScValidator {
    private JsonScValidator(){}

    static boolean validate(JsonNode toCheck) throws ImportationException {
        if(SchemaValidator.isInvalid(toCheck,"scenario-schema.json")) {
            return false;
        }

        int scId = toCheck.get(JsonSCImporter.SC_ID_KEY).asInt();
        ArrayNode consumptionNode = (ArrayNode) toCheck.get(JsonSCImporter.CONSUMPTIONS_KEY);
        ArrayNode openFuses = (ArrayNode) toCheck.get(JsonSCImporter.OPEN_FUSES_KEY);
        ArrayNode uncertainFuses = (ArrayNode) toCheck.get(JsonSCImporter.UC_FUSES_KEY);

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
                double confLvl = ucNode.get(JsonSCImporter.UC_CONF_LVL_KEY).asDouble();
                if(confLvl < 0 || confLvl > 1) {
                    return false;
                }

                int id = ucNode.get(JsonSCImporter.UC_ID_KEY).asInt();
                if(id < 1 || id > nbFuses) {
                    return false;
                }
            }
        }

        return true;

    }
}
