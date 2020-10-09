package duc.sg.java.server.message;

enum MessageType {
    ACTION_LIST("ActionList"),
    LOAD_APPROX("LoadApproximation"),
    ULOAD_APPROX("ULoadApproximation"),
    LOAD_APPROX_ANSWER("LoadApproximationAnswer"),
    ULOAD_APPROX_ANSWER("ULoadApproximationAnswer");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

