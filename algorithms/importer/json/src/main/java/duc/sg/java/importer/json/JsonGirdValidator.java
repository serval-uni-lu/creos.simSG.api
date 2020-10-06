package duc.sg.java.importer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import duc.sg.java.importer.ImportationException;

import java.util.HashSet;
import java.util.Iterator;

class JsonGirdValidator {
    private JsonGirdValidator(){}

    static boolean validate(JsonNode toCheck) throws ImportationException {
        if(SchemaValidator.isInvalid(toCheck,"sg-schema.json")) {
            return false;
        }

        // Get all fuses ID
        var fuses = (ArrayNode) toCheck.get(JsonGridImporter.FUSES);
        final var fusesId = new HashSet<Integer>(fuses.size());
        if (JsonUtils.extractIds(fuses,fusesId, JsonGridImporter.FUSE_ID)) return false;
        if (checkUniqueText(fuses, JsonGridImporter.FUSE_NAME)) return false;

        // Get all cables ID
        var cables = (ArrayNode) toCheck.get(JsonGridImporter.CABLES);
        if (checkUniqueInt(cables, JsonGridImporter.CABLE_ID)) return false;

        // Check that all cable.fuses exist
        for(JsonNode cable: cables) {
            var cableFuses = (ArrayNode) cable.get(JsonGridImporter.CABLE_FUSES);
            for(JsonNode idFuse: cableFuses) {
                if(!fusesId.contains(idFuse.asInt())) return false;
            }
        }

        // Check that there is at least 1 substation
        var entities = (ArrayNode) toCheck.get(JsonGridImporter.ENTITIES);
        if (checkUniqueText(entities, JsonGridImporter.ENT_NAME)) return false;
        var containsSubstation = false;
        Iterator<JsonNode> itElmts = entities.elements();
        while (itElmts.hasNext() && !containsSubstation) {
            JsonNode elmt = itElmts.next();
            String type = elmt.get(JsonGridImporter.ENT_TYPE).asText();
            containsSubstation = type.equalsIgnoreCase("substation");
        }

        if(!containsSubstation)return false;


        // Check that entities.fuses correspond to existing fuses
        for(JsonNode entity: entities) {
            for(JsonNode idFuse: entity.get(JsonGridImporter.ENT_FUSES)) {
                if(!fusesId.contains(idFuse.asInt())) return false;
            }
        }

        for(JsonNode fuse: fuses) {
            // Check that sum(fuse.loads.confidence) = 1 for each fuse
            var loads = fuse.get(JsonGridImporter.FUSE_LOAD);
            if(loads != null && loads.size() > 0) {
                double sum = 0;
                for (JsonNode load : loads) {
                    sum += load.get(JsonGridImporter.FUSE_LOAD_CONF).asDouble();
                }
                if (sum != 1) return false;
            }
        }


        return true;
    }

    static boolean checkUniqueInt(ArrayNode array, String idKey) {
        final HashSet<Integer> ids = new HashSet<>(array.size());
        for (JsonNode elmt : array) {
            var id = elmt.get(idKey).asInt();
            if(!ids.add(id)) {
                return true;
            }
        }
        return false;
    }

    static boolean checkUniqueText(ArrayNode array, String idKey) {
        final HashSet<String> ids = new HashSet<>(array.size());
        for (JsonNode elmt : array) {
            var id = elmt.get(idKey).asText();
            if(!ids.add(id)) {
                return true;
            }
        }
        return false;
    }
}
