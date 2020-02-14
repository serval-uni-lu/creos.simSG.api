package duc.aintea.server.worker;

import duc.aintea.loadapproximation.LoadApproximator;
import duc.aintea.server.messages.ActionRequest;
import duc.aintea.server.messages.ActionResult;
import duc.aintea.server.messages.Error;
import duc.aintea.server.messages.Message;
import duc.aintea.sg.Cable;
import duc.aintea.sg.Fuse;
import duc.aintea.sg.Substation;
import duc.aintea.sg.scenarios.*;

import java.util.ArrayList;

public class SCBasedApprox {

    public static Message execute(ActionRequest request) {
        var result = new ActionResult();
        result.setActionID(request.getActionID());

        Substation substation;
        Fuse[] fuses;
        Cable[] cables;
        switch (request.getScenario()) {
            case 1: {
                substation = SingleCableBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
                fuses = SingleCableBuilder.extractFuses(substation);
                cables = SingleCableBuilder.extractCables(substation);
                break;
            }
            case 2: {
                substation = CabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
                fuses = CabinetBuilder.extractFuses(substation);
                cables = CabinetBuilder.extractCables(substation);
                break;
            }
            case 3: {
                substation = ParaTransformerBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
                fuses = ParaTransformerBuilder.extractFuses(substation);
                cables = ParaTransformerBuilder.extractCables(substation);
                break;
            }
            case 4: {
                substation = ParaCabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
                fuses = ParaCabinetBuilder.extractFuses(substation);
                cables = ParaCabinetBuilder.extractCables(substation);
                break;
            }
//            case 5: {
//                substation = CabinetBuilder.build(request.getFuseStatesArr(), request.getConsumptionsArr());
//                fuses = CabinetBuilder.extractFuses(substation);
//                cables = CabinetBuilder.extractCables(substation);
//                break;
//            }
            default: {
                return new Error(request.getActionID(), "Scenario code " + request.getScenario() + " is not supported by the actuator.");
            }
        }
        LoadApproximator.approximate(substation);

        var cableLoads = new ArrayList<Double>(cables.length);
        for (var cable: cables) {
            cableLoads.add(cable.getLoad());
        }
        result.setCableLoads(cableLoads);

        var fuseLoads = new ArrayList<Double>(fuses.length);
        for (var fuse: fuses) {
            fuseLoads.add(fuse.getLoad());
        }
        result.setFuseLoads(fuseLoads);

        return result;
    }

}
