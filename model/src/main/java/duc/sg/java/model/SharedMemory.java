package duc.sg.java.model;

import java.util.HashMap;
import java.util.Map;

class SharedMemory {
    private Map<String, Object> data;

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
