package duc.sg.java.importer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.HashSet;

class JsonUtils {
    private JsonUtils(){}

    static boolean extractIds(ArrayNode array, final HashSet<Integer> ids, String idKey) {
        for (JsonNode elmt : array) {
            var id = elmt.get(idKey).asInt();
            if(!ids.add(id)) {
                return true;
            }
        }
        return false;
    }
}
