package duc.sg.java.server.ws.messages;

import java.util.ArrayList;
import java.util.List;

public class ActionResult extends Message{
    private Long actionID;
    private Long executionID;
    private List<Double> cableLoads;
    private List<Double> fuseLoads;

    private List<List<Double>> uFuseLoads;
    private List<List<Double>> uFuseConf;

    private List<List<Double>> uCableLoads;
    private List<List<Double>> uCableConf;



    public ActionResult() {
        cableLoads = new ArrayList<>();
        fuseLoads = new ArrayList<>();
        messageType = MessageType.ACTION_RESULT;
        uFuseLoads = new ArrayList<>();
        uFuseConf = new ArrayList<>();
        uCableLoads = new ArrayList<>();
        uCableConf = new ArrayList<>();
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

    public Long getExecutionID() {
        return executionID;
    }

    public void setExecutionID(Long executionID) {
        this.executionID = executionID;
    }


    public List<List<Double>> getuFuseLoads() {
        return uFuseLoads;
    }

    public List<List<Double>> getuFuseConf() {
        return uFuseConf;
    }

    public List<List<Double>> getuCableLoads() {
        return uCableLoads;
    }

    public List<List<Double>> getuCableConf() {
        return uCableConf;
    }

    public void setUncertainFuseLoads(List<List<Double>> fuseLoads, List<List<Double>> fuseLoadsConf) {
        this.uFuseLoads= fuseLoads;
        this.uFuseConf = fuseLoadsConf;
    }

    public void setUncertainCableLoads(List<List<Double>> cableLoads, List<List<Double>> cableLoadsConf) {
        this.uCableLoads = cableLoads;
        this.uCableConf = cableLoadsConf;
    }
}
