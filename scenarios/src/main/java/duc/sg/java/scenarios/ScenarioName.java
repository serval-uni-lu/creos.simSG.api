package duc.sg.java.scenarios;

public enum ScenarioName {
    SINGLE_CABLE(2),
    CABINET(6),
    PARA_TRANSFORMER(6),
    PARA_W_DEADEND(4),
    PARA_CABINET(8),
    INDIRECT_PARALLEL(10),
    LINKED_SUBSTATIONS(4),
    UNDEFINED(-1);


    private int nbFuses;

    ScenarioName(int nbFuses) {
        this.nbFuses = nbFuses;
    }

    public int getNbFuses() {
        return nbFuses;
    }

    public static ScenarioName idToName(int id) {
        return switch (id) {
            case 1 -> SINGLE_CABLE;
            case 2 -> CABINET;
            case 3 -> PARA_TRANSFORMER;
            case 4 -> PARA_CABINET;
            case 5 -> INDIRECT_PARALLEL;
            case 6 -> PARA_W_DEADEND;
            case 7 -> LINKED_SUBSTATIONS;
            default -> UNDEFINED;
        };
    }
}
