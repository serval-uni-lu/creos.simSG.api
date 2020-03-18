package duc.aintea.sg.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jackson.JsonLoader;
import duc.aintea.sg.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonImporter {
    private JsonImporter() {}

    private static final String ENTITIES = "entities";
    private static final String FUSES = "fuses";
    private static final String CABLES = "cables";

    private static final String ENT_TYPE = "type";
    private static final String ENT_NAME = "name";
    private static final String ENT_FUSES = "fuses";

    private static final String FUSE_ID = "id";
    private static final String FUSE_NAME = "name";
    private static final String FUSE_STATE = "state";
    private static final String FUSE_LOAD = "load";

    private static final String FUSE_STATE_STATUS = "status";
    private static final String FUSE_STATE_CONF = "confidence";

    private static final String FUSE_LOAD_CONF = "confidence";
    private static final String FUSE_LOAD_VAL = "value";

    private static final String CABLE_ID = "id";
    private static final String CABLE_METERS = "meters";
    private static final String CABLE_FUSES = "fuses";

    private static final String CABLE_METER_NAME = "name";
    private static final String CABLE_METER_CONSUMPTION = "consumption";


    private static boolean validate(JsonNode toCheck) throws ValidationException {
        if(!SchemaValidator.validate(toCheck,"./sg-schema.json")) {
            return false;
        }

        // Get all fuses ID
        var fuses = (ArrayNode) toCheck.get(FUSES);
        final var fusesId = new HashSet<Integer>(fuses.size());
        if (extractIds(fuses,fusesId, FUSE_ID)) return false;
        if (checkUniqueText(fuses, FUSE_NAME)) return false;

        // Get all cables ID
        var cables = (ArrayNode) toCheck.get(CABLES);
        if (checkUniqueInt(cables, CABLE_ID)) return false;

        // Check that all cable.fuses exist
        for(JsonNode cable: cables) {
            var cableFuses = (ArrayNode) cable.get(CABLE_FUSES);
            for(JsonNode idFuse: cableFuses) {
                if(!fusesId.contains(idFuse.asInt())) return false;
            }
        }

        // Check that there is at least 1 substation
        var entities = (ArrayNode) toCheck.get(ENTITIES);
        if (checkUniqueText(entities, ENT_NAME)) return false;
        var containsSubstation = false;
        Iterator<JsonNode> itElmts = entities.elements();
        while (itElmts.hasNext() && !containsSubstation) {
            JsonNode elmt = itElmts.next();
            String type = elmt.get(ENT_TYPE).asText();
            containsSubstation = type.equalsIgnoreCase("substation");
        }

        if(!containsSubstation)return false;


        // Check that entities.fuses correspond to existing fuses
        for(JsonNode entity: entities) {
            for(JsonNode idFuse: entity.get(ENT_FUSES)) {
                if(!fusesId.contains(idFuse.asInt())) return false;
            }
        }

        for(JsonNode fuse: fuses) {
            // Check that sum(fuse.loads.confidence) = 1 for each fuse
            var loads = fuse.get(FUSE_LOAD);
            if(loads != null) {
                double sum = 0;
                for (JsonNode load : loads) {
                    sum += load.get(FUSE_LOAD_CONF).asDouble();
                }
                if (sum != 1) return false;
            }
        }


        return true;
    }

    private static boolean checkUniqueInt(ArrayNode array, String idKey) {
        final HashSet<Integer> ids = new HashSet<>(array.size());
        for (JsonNode elmt : array) {
            var id = elmt.get(idKey).asInt();
            if(!ids.add(id)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkUniqueText(ArrayNode array, String idKey) {
        final HashSet<String> ids = new HashSet<>(array.size());
        for (JsonNode elmt : array) {
            var id = elmt.get(idKey).asText();
            if(!ids.add(id)) {
                return true;
            }
        }
        return false;
    }

    private static boolean extractIds(ArrayNode array, final HashSet<Integer> ids, String idKey) {
        for (JsonNode elmt : array) {
            var id = elmt.get(idKey).asInt();
            if(!ids.add(id)) {
                return true;
            }
        }
        return false;
    }

    public static Optional<List<Substation>> from(File jsonFile) throws ValidationException {
        JsonNode toImport;
        try {
            toImport = JsonLoader.fromFile(jsonFile);
        } catch (IOException e) {
            throw new ValidationException(e);
        }

        if(validate(toImport)) {
            HashMap<Integer, Fuse> fuses = extractFuses(toImport);
            extractCables(toImport, fuses);
            return Optional.of(extractEntities(toImport, fuses));
        } else {
            return Optional.empty();
        }

    }

    private static List<Substation> extractEntities(JsonNode toImport, HashMap<Integer, Fuse> fuses) {
        var entitiesNodes = (ArrayNode) toImport.get(ENTITIES);
        final var listSubstations = new ArrayList<Substation>(entitiesNodes.size());
        entitiesNodes.forEach(entityNode -> {
            var type = entityNode.get(ENT_TYPE).asText();
            var name = entityNode.get(ENT_NAME).asText();
            Entity entity;
            if(type.equalsIgnoreCase("substation")) {
                entity = new Substation(name);
                listSubstations.add((Substation) entity);
            } else {
                entity = new Cabinet(name);
            }

            var fusesNode = (ArrayNode) entityNode.get(ENT_FUSES);
            var entFuses = new Fuse[fusesNode.size()];
            for (int i = 0; i < fusesNode.size(); i++) {
                var fuseIdx = fusesNode.get(i).asInt();
                entFuses[i] = fuses.get(fuseIdx);
            }
            entity.addFuses(entFuses);

        });


        return listSubstations;
    }

    private static HashMap<Integer, Fuse> extractFuses(JsonNode toImport) {
        var fuseArrayNode = (ArrayNode) toImport.get(FUSES);
        final var fuses = new HashMap<Integer, Fuse>(fuseArrayNode.size());
        fuseArrayNode.forEach(fuseNode -> {
            var fuse = new Fuse(fuseNode.get(FUSE_NAME).asText());
            fuses.put(fuseNode.get(FUSE_ID).asInt(), fuse);
            var stateNode = fuseNode.get(FUSE_STATE);

            if(!stateNode.get(FUSE_STATE_STATUS).asText().equalsIgnoreCase("closed")) {
                fuse.openFuse();
            }
            fuse.getStatus().setConfAsProb(stateNode.get(FUSE_STATE_CONF).asDouble());

            var loads = (ArrayNode) fuseNode.get(FUSE_LOAD);
            if(loads != null) {
                var uLoad = new HashMap<Double, Double>(loads.size());
                loads.forEach(loadNode -> {
                    uLoad.put(loadNode.get(FUSE_LOAD_VAL).asDouble(), loadNode.get(FUSE_LOAD_CONF).asDouble());
                });
                fuse.setLoad(uLoad);
            }

        });
        return fuses;
    }

    private static void extractCables(JsonNode toImport, HashMap<Integer, Fuse> fuses) {
        var cableArrayNode = (ArrayNode) toImport.get(CABLES);
        cableArrayNode.forEach(cableNode -> {
            final var cable = new Cable();
            var metersNodes = (ArrayNode) cableNode.get(CABLE_METERS);
            metersNodes.forEach(meterNode -> {
                var meter = new Meter(meterNode.get(CABLE_METER_NAME).asText());
                meter.setConsumption(meterNode.get(CABLE_METER_CONSUMPTION).asDouble());
                cable.addMeters(meter);
            });
            var fusesIds = (ArrayNode) cableNode.get(CABLE_FUSES);
            var fuse1 = fuses.get(fusesIds.get(0).asInt());
            var fuse2 = fuses.get(fusesIds.get(1).asInt());
            cable.setFuses(fuse1, fuse2);
        });
    }

}
