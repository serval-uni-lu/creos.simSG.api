package duc.aintea.server.messages;

public class Error extends Message{
    private Long actionID;
    private String reason;

    public Error(long actionID, String reason) {
        this.actionID = actionID;
        this.reason = reason;
        this.messageType = MessageType.ERROR;
    }


    public String getMessageType() {
        return messageType.getType();
    }

    public Long getActionID() {
        return actionID;
    }

    public String getReason() {
        return reason;
    }


}
