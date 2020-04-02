package duc.sg.java.scenarios;

public enum ScenarioName {
    SINGLE_CABLE(2),
    CABINET(6),
    PARA_TRANSFORMER(6),
    PARA_CABINET(8),
    INDIRECT_PARALLEL(10),
    UNDEFINED(-1);

    private int nbFuses;

    ScenarioName(int nbFuses) {
        this.nbFuses = nbFuses;
    }

    public int getNbFuses() {
        return nbFuses;
    }

    public static ScenarioName idToName(int id) {
        switch (id) {
            case 1: return SINGLE_CABLE;
            case 2: return CABINET;
            case 3: return PARA_TRANSFORMER;
            case 4: return PARA_CABINET;
            case 5: return INDIRECT_PARALLEL;
            default: return UNDEFINED;
        }
    }
}
