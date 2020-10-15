package duc.sg.java.server.message;

/**
 * The different message type that are exchanges between a server and a client.
 *
 * **WARNING**: if you modify this list, do not forget to update the message type on the client side.
 * The generation of the JSON uses the {@link MessageType#toString()} method.
 */
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

