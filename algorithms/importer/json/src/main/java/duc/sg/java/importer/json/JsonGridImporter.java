package duc.sg.java.importer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import duc.sg.java.importer.ImportationException;
import duc.sg.java.model.*;
import duc.sg.java.uncertainty.MultDblPoss2;
import duc.sg.java.uncertainty.PossibilityDouble;

import java.util.HashMap;
import java.util.Optional;

public class JsonGridImporter extends JsonImporter<SmartGrid> {
    private JsonGridImporter() {}

    public static final JsonGridImporter INSTANCE = new JsonGridImporter();

    static final String ENTITIES = "entities";
    static final String FUSES = "fuses";
    static final String CABLES = "cables";

    static final String ENT_TYPE = "type";
    static final String ENT_NAME = "name";
    static final String ENT_FUSES = "fuses";

    static final String FUSE_ID = "id";
    static final String FUSE_NAME = "name";
    static final String FUSE_STATE = "state";
    static final String FUSE_LOAD = "load";

    static final String FUSE_STATE_STATUS = "status";
    static final String FUSE_STATE_CONF = "confidence";

    static final String FUSE_LOAD_CONF = "confidence";
    static final String FUSE_LOAD_VAL = "value";

    static final String CABLE_ID = "id";
    static final String CABLE_METERS = "meters";
    static final String CABLE_FUSES = "fuses";

    static final String CABLE_METER_NAME = "name";
    static final String CABLE_METER_CONSUMPTION = "consumption";

    @Override
    protected Optional<SmartGrid> extract(JsonNode toImport) throws ImportationException {
        if(JsonGirdValidator.validate(toImport)) {
            HashMap<Integer, Fuse> fuses = extractFuses(toImport);
            extractCables(toImport, fuses);
            return Optional.of(extractEntities(toImport, fuses));
        } else {
            return Optional.empty();
        }
    }

    private SmartGrid extractEntities(JsonNode toImport, HashMap<Integer, Fuse> fuses) {
        var entitiesNodes = (ArrayNode) toImport.get(ENTITIES);
        var sg = new SmartGrid();

        entitiesNodes.forEach(entityNode -> {
            var type = entityNode.get(ENT_TYPE).asText();
            var name = entityNode.get(ENT_NAME).asText();
            Entity entity;
            if(type.equalsIgnoreCase("substation")) {
                entity = new Substation(name);
                sg.addSubstations((Substation) entity);
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

        return sg;
    }

    private HashMap<Integer, Fuse> extractFuses(JsonNode toImport) {
        var fuseArrayNode = (ArrayNode) toImport.get(FUSES);
        final var fuses = new HashMap<Integer, Fuse>(fuseArrayNode.size());
        fuseArrayNode.forEach(fuseNode -> {
            var fuse = new Fuse(fuseNode.get(FUSE_NAME).asText());
            fuses.put(fuseNode.get(FUSE_ID).asInt(), fuse);

            var stateNode = fuseNode.get(FUSE_STATE);
            if(stateNode != null) {
                if (!stateNode.get(FUSE_STATE_STATUS).asText().equalsIgnoreCase("closed")) {
                    fuse.openFuse();
                    fuse.getStatus().setConfIsOpen(stateNode.get(FUSE_STATE_CONF).asDouble());
                } else {
                    fuse.getStatus().setConfIsClosed(stateNode.get(FUSE_STATE_CONF).asDouble());
                }

            }

            var loads = (ArrayNode) fuseNode.get(FUSE_LOAD);
            if(loads != null) {
                final var uLoad = new MultDblPoss2();
                loads.forEach(loadNode -> {
                    var possibility = new PossibilityDouble(
                            loadNode.get(FUSE_LOAD_VAL).asDouble(),
                            loadNode.get(FUSE_LOAD_CONF).asDouble()
                    );
                    uLoad.addPossibility(possibility);
                });
                fuse.setLoad(uLoad);
            }

        });
        return fuses;
    }

    private void extractCables(JsonNode toImport, HashMap<Integer, Fuse> fuses) {
        var cableArrayNode = (ArrayNode) toImport.get(CABLES);
        cableArrayNode.forEach(cableNode -> {
            final var cable = new Cable();

            var metersNodes = (ArrayNode) cableNode.get(CABLE_METERS);
            if(metersNodes != null) {
                metersNodes.forEach(meterNode -> {
                    var meter = new Meter(meterNode.get(CABLE_METER_NAME).asText());
                    meter.setConsumption(meterNode.get(CABLE_METER_CONSUMPTION).asDouble());
                    cable.addMeters(meter);
                });
            }

            var fusesIds = (ArrayNode) cableNode.get(CABLE_FUSES);
            var fuse1 = fuses.get(fusesIds.get(0).asInt());
            var fuse2 = fuses.get(fusesIds.get(1).asInt());
            cable.setFuses(fuse1, fuse2);
        });
    }

}
