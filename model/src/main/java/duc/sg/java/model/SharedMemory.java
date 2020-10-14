package duc.sg.java.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A big map to cache the result of the different algorithms.
 * This class should be re-designed. Please see
 * <a href="https://github.com/UL-SnT-Serval/creos.simSG.api/issues/3">Issue 3</a>
 */
class SharedMemory {
    private final Map<String, Object> data;

    SharedMemory() {
        data = new HashMap<>();
    }

    public void save(String key, Object info) {
        data.put(key, info);
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public Object get(String key) {
        return data.get(key);
    }



}
