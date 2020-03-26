package duc.sg.java.server.ws.messages;

import java.util.ArrayList;
import java.util.List;

public class ActionRequest {
    private Long actionID;
    private Long executionID;
    private Integer scenario;
    private List<Boolean> fuseStates = new ArrayList<>();
    private List<Double> consumptions = new ArrayList<>();
    private List<Double> fuseConfidence = new ArrayList<>();

    public Long getActionID() {
        return actionID;
    }

    public void setActionID(Long actionID) {
        this.actionID = actionID;
    }

    public Integer getScenario() {
        return scenario;
    }

    public void setScenario(Integer scenario) {
        this.scenario = scenario;
    }

    public List<Boolean> getFuseStates() {
        return fuseStates;
    }

    public boolean[] getFuseStatesArr() {
        var res = new boolean[fuseStates.size()];
        for (int i = 0; i < fuseStates.size(); i++) {
            var fs = fuseStates.get(i);
            res[i] = fs;
        }
        return res;
    }

    public void setFuseStates(List<Boolean> fuseStates) {
        this.fuseStates = fuseStates;
    }

    public List<Double> getConsumptions() {
        return consumptions;
    }

    public void setConsumptions(List<Double> consumptions) {
        this.consumptions = consumptions;
    }

    public void addConsumption(Double c) {
        consumptions.add(c);
    }

    public List<Double> getFuseConfidence() {
        return fuseConfidence;
    }

    public void setFuseConfidence(List<Double> fuseConfidence) {
        this.fuseConfidence = fuseConfidence;
    }

    public void addFuseConfidence(Double c) {
        fuseConfidence.add(c);
    }

    public Long getExecutionID() {
        return executionID;
    }

    public void setExecutionID(Long executionID) {
        this.executionID = executionID;
    }

    public double[] getConsumptionsArr() {
        var res = new double[consumptions.size()];
        for (int i = 0; i < consumptions.size(); i++) {
            var c = consumptions.get(i);
            res[i] = c;
        }
        return res;
    }
}
