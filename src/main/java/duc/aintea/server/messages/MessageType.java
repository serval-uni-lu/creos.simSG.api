package duc.aintea.server.messages;

public enum MessageType {
    REQUEST_ACTION("request_action"),
    ACTION_RESULT("action_result"),
    ACTUATOR_HELLO("actuator_hello"),
    ERROR("error");


    private String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
