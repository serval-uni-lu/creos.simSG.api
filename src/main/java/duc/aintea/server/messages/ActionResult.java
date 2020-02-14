package duc.aintea.server.messages;

import java.util.ArrayList;
import java.util.List;

public class ActionResult extends Message{
    private Long actionID;
    private List<Double> cableLoads;
    private List<Double> fuseLoads;


    public ActionResult() {
        cableLoads = new ArrayList<>();
        fuseLoads = new ArrayList<>();
        messageType = MessageType.ACTION_RESULT;
    }

    public String getMessageType() {
        return messageType.getType();
    }

    public Long getActionID() {
        return actionID;
    }

    public void setActionID(Long actionID) {
        this.actionID = actionID;
    }

    public List<Double> getCableLoads() {
        return cableLoads;
    }

    public void setCableLoads(List<Double> cableLoads) {
        this.cableLoads = cableLoads;
    }

    public List<Double> getFuseLoads() {
        return fuseLoads;
    }

    public void setFuseLoads(List<Double> fuseLoads) {
        this.fuseLoads = fuseLoads;
    }
}
