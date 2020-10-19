package creos.simsg.api.server.ws.message;

/**
 * Message sent to the client on the connection, to inform it the actuators the server handles.
 */
public class ActionListMsg extends Message {
    private String[] actionName;

    public ActionListMsg(String... actionName) {
        super(MessageType.ACTION_LIST);
        this.actionName = actionName;
    }

    public String[] getActionName() {
        return actionName;
    }

    public void setActionName(String[] actionName) {
        this.actionName = actionName;
    }
}
