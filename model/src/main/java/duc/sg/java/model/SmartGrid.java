package duc.sg.java.model;

import java.util.*;

public class SmartGrid {
    private Map<String, Substation> substations;

    public SmartGrid() {
        this.substations = new HashMap<>();
    }

    public Optional<Substation> getSubstation(String name) {
        if(substations.containsKey(name)) return Optional.of(substations.get(name));
        return Optional.empty();
    }

    public void addSubstations(Substation substation) {
        this.substations.put(substation.getName(), substation);
    }

    public Collection<Substation> getSubstations() {
        return Collections.unmodifiableCollection(substations.values());
    }
}
