package duc.sg.java.model;

import java.util.*;

public class SmartGrid {
    private Map<String, Substation> substations;
    private SharedMemory memory;

    public SmartGrid() {
        this.substations = new HashMap<>();
        this.memory = new SharedMemory();
    }

    public Optional<Substation> getSubstation(String name) {
        if(substations.containsKey(name)) return Optional.of(substations.get(name));
        return Optional.empty();
    }

    public void addSubstations(Substation substation) {
        this.substations.put(substation.getName(), substation);
        substation.setGrid(this);
    }

    public Collection<Substation> getSubstations() {
        return Collections.unmodifiableCollection(substations.values());
    }

    public void save(String key, Object data) {
        this.memory.save(key, data);
    }

    public Optional<Object> retrieve(String key) {
        return Optional.ofNullable(this.memory.get(key));
    }

}
