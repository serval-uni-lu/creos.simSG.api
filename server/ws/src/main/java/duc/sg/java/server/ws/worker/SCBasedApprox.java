package duc.sg.java.server.ws.worker;

import duc.sg.java.loadapproximator.loadapproximation.LoadApproximator;
import duc.sg.java.loadapproximator.loadapproximation.UncertainLoadApproximator;
import duc.sg.java.model.Cable;
import duc.sg.java.model.Fuse;
import duc.sg.java.scenarios.Scenario;
import duc.sg.java.scenarios.ScenarioBuilder;
import duc.sg.java.scenarios.ScenarioName;
import duc.sg.java.server.ws.messages.ActionRequest;
import duc.sg.java.server.ws.messages.ActionResult;
import duc.sg.java.server.ws.messages.Error;
import duc.sg.java.server.ws.messages.Message;
import duc.sg.java.uncertainty.MultDblePossibilities;
import duc.sg.java.uncertainty.PossibilityDouble;

import java.util.ArrayList;
import java.util.List;

public class SCBasedApprox {

    public static Message execute(ActionRequest request) {
        if(request.getActionID() == 1) {
            return executeAction1(request);
        } else if(request.getActionID() == 2) {
            return executeAction2(request);
        }

        return new Error(request.getActionID(),
                request.getExecutionID(),
                "Action ID " + request.getActionID() + " not supported by the actuator");
    }

    private static Message executeAction1(ActionRequest request) {
        var result = new ActionResult();
        result.setActionID(request.getActionID());
        result.setExecutionID(request.getExecutionID());

//        Substation substation;
//        Fuse[] fuses;
//        Cable[] cables;
        ScenarioName scenarioName;
        switch (request.getScenario()) {
            case 1: {
                scenarioName = ScenarioName.SINGLE_CABLE;
//                substation = SingleCableBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = SingleCableBuilder.extractFuses(substation);
//                cables = SingleCableBuilder.extractCables(substation);
                break;
            }
            case 2: {
                scenarioName = ScenarioName.CABINET;
//                substation = CabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = CabinetBuilder.extractFuses(substation);
//                cables = CabinetBuilder.extractCables(substation);
                break;
            }
            case 3: {
                scenarioName = ScenarioName.PARA_TRANSFORMER;
//                substation = ParaTransformerBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = ParaTransformerBuilder.extractFuses(substation);
//                cables = ParaTransformerBuilder.extractCables(substation);
                break;
            }
            case 4: {
                scenarioName = ScenarioName.PARA_CABINET;
//                substation = ParaCabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = ParaCabinetBuilder.extractFuses(substation);
//                cables = ParaCabinetBuilder.extractCables(substation);
                break;
            }
            case 5: {
                scenarioName = ScenarioName.INDIRECT_PARALLEL;
//                substation = IndirectParaBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = IndirectParaBuilder.extractFuses(substation);
//                cables = IndirectParaBuilder.extractCables(substation);
                break;
            }
            default: {
                return new Error(request.getActionID(),
                        request.getExecutionID(),
                        "Scenario code " + request.getScenario() + " is not supported by the actuator.");
            }
        }

        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(scenarioName)
                .build();

        LoadApproximator.approximate(scenario.getSubstation());

        Fuse[] fuses = scenario.extractFuses();
        Cable[] cables = scenario.extractCables();

        var cableLoads = new ArrayList<Double>(cables.length);
        for (var cable : cables) {
            MultDblePossibilities multPoss = cable.getUncertainLoad();
            PossibilityDouble possibilityDouble = multPoss.iterator().next();
            cableLoads.add(possibilityDouble.getValue());
        }
        result.setCableLoads(cableLoads);

        var fuseLoads = new ArrayList<Double>(fuses.length);
        for (var fuse : fuses) {
            MultDblePossibilities multPoss = fuse.getUncertainLoad();
            PossibilityDouble possibilityDouble = multPoss.iterator().next();
            fuseLoads.add(possibilityDouble.getValue());
        }
        result.setFuseLoads(fuseLoads);

        return result;
    }


    private static Message executeAction2(ActionRequest request) {
        var result = new ActionResult();
        result.setActionID(request.getActionID());
        result.setExecutionID(request.getExecutionID());

//        Substation substation;
//        Fuse[] fuses;
//        Cable[] cables;
//        switch (request.getScenario()) {
//            case 1: {
//                substation = SingleCableBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = SingleCableBuilder.extractFuses(substation);
//                cables = SingleCableBuilder.extractCables(substation);
//                break;
//            }
//            case 2: {
//                substation = CabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = CabinetBuilder.extractFuses(substation);
//                cables = CabinetBuilder.extractCables(substation);
//                break;
//            }
//            case 3: {
//                substation = ParaTransformerBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = ParaTransformerBuilder.extractFuses(substation);
//                cables = ParaTransformerBuilder.extractCables(substation);
//                break;
//            }
//            case 4: {
//                substation = ParaCabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = ParaCabinetBuilder.extractFuses(substation);
//                cables = ParaCabinetBuilder.extractCables(substation);
//                break;
//            }
//            case 5: {
//                substation = IndirectParaBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = IndirectParaBuilder.extractFuses(substation);
//                cables = IndirectParaBuilder.extractCables(substation);
//                break;
//            }
//            default: {
//                return new Error(request.getActionID(),
//                        request.getExecutionID(),
//                        "Scenario code " + request.getScenario() + " is not supported by the actuator.");
//            }
//        }

        ScenarioName scenarioName;
        switch (request.getScenario()) {
            case 1: {
                scenarioName = ScenarioName.SINGLE_CABLE;
//                substation = SingleCableBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = SingleCableBuilder.extractFuses(substation);
//                cables = SingleCableBuilder.extractCables(substation);
                break;
            }
            case 2: {
                scenarioName = ScenarioName.CABINET;
//                substation = CabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = CabinetBuilder.extractFuses(substation);
//                cables = CabinetBuilder.extractCables(substation);
                break;
            }
            case 3: {
                scenarioName = ScenarioName.PARA_TRANSFORMER;
//                substation = ParaTransformerBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = ParaTransformerBuilder.extractFuses(substation);
//                cables = ParaTransformerBuilder.extractCables(substation);
                break;
            }
            case 4: {
                scenarioName = ScenarioName.PARA_CABINET;
//                substation = ParaCabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = ParaCabinetBuilder.extractFuses(substation);
//                cables = ParaCabinetBuilder.extractCables(substation);
                break;
            }
            case 5: {
                scenarioName = ScenarioName.INDIRECT_PARALLEL;
//                substation = IndirectParaBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = IndirectParaBuilder.extractFuses(substation);
//                cables = IndirectParaBuilder.extractCables(substation);
                break;
            }
            default: {
                return new Error(request.getActionID(),
                        request.getExecutionID(),
                        "Scenario code " + request.getScenario() + " is not supported by the actuator.");
            }
        }

        Scenario scenario = new ScenarioBuilder()
                .chooseScenario(scenarioName)
                .build();
        Fuse[] fuses = scenario.extractFuses();
        Cable[] cables = scenario.extractCables();

        var fuseConf = request.getFuseConfidence();
        for (int i = 0; i < fuses.length; i++) {
            Fuse fuse = fuses[i];
//            fuse.getStatus().setConfAsPercentage(fuseConf.get(i));
            fuse.getStatus().setConfIsClosed(fuseConf.get(i));
        }


        UncertainLoadApproximator.approximate(scenario.getSubstation());

        var cableLoads = new ArrayList<List<Double>>(cables.length);
        var cableLoadsConf = new ArrayList<List<Double>>(cables.length);
        for (var cable : cables) {
            var uLoad = cable.getUncertainLoad();
            var currLoads = new ArrayList<Double>();
            var currConf = new ArrayList<Double>();

//            for(var entry: uLoad.entrySet()) {
//                currLoads.add(entry.getKey());
//                currConf.add(entry.getValue());
//            }

            for(PossibilityDouble possibility: uLoad) {
                currLoads.add(possibility.getValue());
                currConf.add(possibility.getConfidence().getProbability());
            }

            cableLoads.add(currLoads);
            cableLoadsConf.add(currConf);
        }
        result.setUncertainCableLoads(cableLoads, cableLoadsConf);

        var fuseLoads = new ArrayList<List<Double>>(fuses.length);
        var fuseLoadsConf = new ArrayList<List<Double>>(fuses.length);
        for (var fuse : fuses) {
            var uLoad = fuse.getUncertainLoad();
            var currLoads = new ArrayList<Double>();
            var currConf = new ArrayList<Double>();

//            for(var entry: uLoad.entrySet()) {
//                currLoads.add(entry.getKey());
//                currConf.add(entry.getValue());
//            }

            for(PossibilityDouble possibility: uLoad) {
                currLoads.add(possibility.getValue());
                currConf.add(possibility.getConfidence().getProbability());
            }


            fuseLoads.add(currLoads);
            fuseLoadsConf.add(currConf);
        }
        result.setUncertainFuseLoads(fuseLoads, fuseLoadsConf);

        return result;
    }

}
