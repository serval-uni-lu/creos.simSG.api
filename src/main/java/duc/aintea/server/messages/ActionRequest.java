package duc.aintea.server.messages;

import java.util.ArrayList;
import java.util.List;

public class ActionRequest {
    private Long actionID;
    private Integer scenario;
    private List<Boolean> fuseStates = new ArrayList<>();
    private List<Double> consumptions = new ArrayList<>();

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


    public double[] getConsumptionsArr() {
        var res = new double[consumptions.size()];
        for (int i = 0; i < consumptions.size(); i++) {
            var c = consumptions.get(i);
            res[i] = c;
        }
        return res;
    }
}
