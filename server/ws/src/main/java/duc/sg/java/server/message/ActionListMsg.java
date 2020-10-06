package duc.sg.java.server.message;

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
