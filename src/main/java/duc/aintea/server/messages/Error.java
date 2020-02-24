package duc.aintea.server.messages;

public class Error extends Message{
    private Long actionID;
    private Long executionID;
    private String reason;

    public Error(long actionID, long executionID, String reason) {
        this.actionID = actionID;
        this.executionID = executionID;
        this.reason = reason;
        this.messageType = MessageType.ERROR;
    }


    public String getMessageType() {
        return messageType.getType();
    }

    public Long getActionID() {
        return actionID;
    }

    public Long getExecutionID() {
        return executionID;
    }

    public String getReason() {
        return reason;
    }


}
