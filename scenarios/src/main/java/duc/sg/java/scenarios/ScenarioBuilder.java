package duc.sg.java.scenarios;

import duc.sg.java.model.*;

import java.util.Arrays;

public final class ScenarioBuilder {
    private ScenarioName name;
    private State[] fuseState;
    private  double[] consumptions;
    private SmartGrid toWrap;

    public ScenarioBuilder chooseScenario(ScenarioName name) {
        this.name = name;
        return this;
    }

    public ScenarioBuilder setFuseStates(State[] fuseState) {
        if(fuseState == null || fuseState.length == 0) {
            throw new BuilderException("fuseState cannot be null or empty");
        }

        this.fuseState = fuseState;
        return this;
    }

    public ScenarioBuilder setConsumptions(double[] consumptions) {
        if(consumptions == null || consumptions.length == 0) {
            throw new BuilderException("consumptions cannot be null");
        }

        this.consumptions = consumptions;
        return this;
    }

    public ScenarioBuilder wrap(SmartGrid grid) {
        this.toWrap = grid;
        return this;
    }

    public Scenario build() {
        if(name == null) {
            throw new BuilderException("Scenario to build has not been defined.");
        }

        if(toWrap != null) {
            return switch (name) {
                case SINGLE_CABLE -> new SingleCableSC(toWrap);
                case CABINET -> new CabinetSC(toWrap);
                case INDIRECT_PARALLEL -> new IndirectParaSC(toWrap);
                case PARA_CABINET -> new ParaCabinetSC(toWrap);
                case PARA_TRANSFORMER -> new ParaTransformerSC(toWrap);
                case PARA_W_DEADEND -> new ParaWithDeadendSC(toWrap);
                default -> throw new BuilderException("Builder for " + name + "has not been implemented yet.");
            };
        }

        return switch (name) {
            case SINGLE_CABLE -> buildSingCable();
            case CABINET -> buildCabinet();
            case INDIRECT_PARALLEL -> buildIndirectPara();
            case PARA_CABINET -> buildParaCabinet();
            case PARA_TRANSFORMER -> buildParaTransformer();
            case PARA_W_DEADEND -> buildParaWDe();
            default -> throw new BuilderException("Builder for " + name + "has not been implemented yet.");
        };
    }

    private void checkFuseStatusParam(int exceptedLength) {
        if(fuseState != null && fuseState.length != exceptedLength) {
            throw new BuilderException("For scenario " + name + ", fuseStatus should contain " + exceptedLength + " elements. Actual: " + fuseState.length);
        }
    }

    private void checkConsumptionsParam(int exceptedLength) {
        if(consumptions != null && consumptions.length != exceptedLength) {
            throw new BuilderException("For scenario " + name + ", consumptions should contain " + exceptedLength + " elements. Actual: " + fuseState.length);
        }
    }

    private void createCable(String f1Name, String f2Name, State f1State, State f2State, Entity ownerf1, Entity ownerf2, double consumption) {
        var f1 = new Fuse(f1Name, f1Name, f1State);
        var f2 = new Fuse(f2Name, f2Name, f2State);
        var cable = new Cable();
        cable.setFuses(f1, f2);

        var meter = new Meter("m1");
        meter.setConsumption(consumption);
        cable.addMeters(meter);

        ownerf1.addFuses(f1);
        ownerf2.addFuses(f2);
    }



    private SingleCableSC buildSingCable() {
        var pFuseState = getOrDefaultFuseStatus(2);
        var pConsumptions = getOrDefaultConsumptions(1);

        var subs = new Substation(SingleCableSC.SUBSTATION_NAME);
        var cab = new Cabinet(SingleCableSC.CABINET_NAME);
        createCable(SingleCableSC.F1_NAME, SingleCableSC.F2_NAME, pFuseState[0], pFuseState[1], subs, cab, pConsumptions[0]);

        var grid = new SmartGrid();
        grid.addSubstations(subs);

        return new SingleCableSC(grid);
    }

    private CabinetSC buildCabinet() {
        var pFuseState = getOrDefaultFuseStatus(6);
        var pConsumptions = getOrDefaultConsumptions(3);

        var subs = new Substation(CabinetSC.SUBSTATION_NAME);
        var cab1 = new Cabinet(CabinetSC.CABINET1_NAME);
        var cab2 = new Cabinet(CabinetSC.CABINET2_NAME);
        var cab3 = new Cabinet(CabinetSC.CABINET3_NAME);

        createCable(CabinetSC.F1_NAME, CabinetSC.F2_NAME, pFuseState[0], pFuseState[1], subs, cab1, pConsumptions[0]);
        createCable(CabinetSC.F3_NAME, CabinetSC.F5_NAME, pFuseState[2], pFuseState[4], cab1, cab2, pConsumptions[1]);
        createCable(CabinetSC.F4_NAME, CabinetSC.F6_NAME, pFuseState[3], pFuseState[5], cab1, cab3, pConsumptions[2]);

        var grid = new SmartGrid();
        grid.addSubstations(subs);
        return new CabinetSC(grid);
    }

    private IndirectParaSC buildIndirectPara() {
        var pFuseState = getOrDefaultFuseStatus(10);
        var pConsumptions = getOrDefaultConsumptions(5);

        var subs = new Substation(IndirectParaSC.SUBSTATION_NAME);
        var cab1 = new Cabinet(IndirectParaSC.CABINET1_NAME);
        var cab2 = new Cabinet(IndirectParaSC.CABINET2_NAME);
        var cab3 = new Cabinet(IndirectParaSC.CABINET3_NAME);
        var cab4 = new Cabinet(IndirectParaSC.CABINET4_NAME);

        createCable(IndirectParaSC.F1_NAME, IndirectParaSC.F2_NAME, pFuseState[0], pFuseState[1], subs, cab2, pConsumptions[0]);
        createCable(IndirectParaSC.F3_NAME, IndirectParaSC.F4_NAME, pFuseState[2], pFuseState[3], subs, cab1, pConsumptions[1]);
        createCable(IndirectParaSC.F5_NAME, IndirectParaSC.F6_NAME, pFuseState[4], pFuseState[5], cab1, cab3, pConsumptions[2]);
        createCable(IndirectParaSC.F7_NAME, IndirectParaSC.F8_NAME, pFuseState[6], pFuseState[7], cab1, cab2, pConsumptions[3]);
        createCable(IndirectParaSC.F9_NAME, IndirectParaSC.F10_NAME, pFuseState[8], pFuseState[9], cab2, cab4, pConsumptions[4]);

        var grid = new SmartGrid();
        grid.addSubstations(subs);
        return new IndirectParaSC(grid);
    }

    private ParaCabinetSC buildParaCabinet() {
        var pFuseState = getOrDefaultFuseStatus(8);
        var pConsumptions = getOrDefaultConsumptions(4);

        var subs = new Substation(ParaCabinetSC.SUBSTATION_NAME);
        var cab1 = new Cabinet(ParaCabinetSC.CABINET1_NAME);
        var cab2 = new Cabinet(ParaCabinetSC.CABINET2_NAME);
        var cab3 = new Cabinet(ParaCabinetSC.CABINET3_NAME);

        createCable(ParaCabinetSC.F1_NAME, ParaCabinetSC.F2_NAME, pFuseState[0], pFuseState[1], subs, cab1, pConsumptions[0]);
        createCable(ParaCabinetSC.F3_NAME, ParaCabinetSC.F4_NAME, pFuseState[2], pFuseState[3], cab1, cab2, pConsumptions[1]);
        createCable(ParaCabinetSC.F5_NAME, ParaCabinetSC.F6_NAME, pFuseState[4], pFuseState[5], cab1, cab2, pConsumptions[2]);
        createCable(ParaCabinetSC.F7_NAME, ParaCabinetSC.F8_NAME, pFuseState[6], pFuseState[7], cab2, cab3, pConsumptions[3]);

        var grid = new SmartGrid();
        grid.addSubstations(subs);
        return new ParaCabinetSC(grid);
    }

    private ParaTransformerSC buildParaTransformer() {
        var pFuseState = getOrDefaultFuseStatus(6);
        var pConsumptions = getOrDefaultConsumptions(3);

        var subs = new Substation(ParaTransformerSC.SUBSTATION_NAME);
        var cab1 = new Cabinet(ParaTransformerSC.CABINET1_NAME);
        var cab2 = new Cabinet(ParaTransformerSC.CABINET2_NAME);

        createCable(ParaTransformerSC.F1_NAME, ParaTransformerSC.F2_NAME, pFuseState[0], pFuseState[1], subs, cab1, pConsumptions[0]);
        createCable(ParaTransformerSC.F3_NAME, ParaTransformerSC.F4_NAME, pFuseState[2], pFuseState[3], subs, cab1, pConsumptions[1]);
        createCable(ParaTransformerSC.F5_NAME, ParaTransformerSC.F6_NAME, pFuseState[4], pFuseState[5], cab1, cab2, pConsumptions[2]);


        var grid = new SmartGrid();
        grid.addSubstations(subs);
        return new ParaTransformerSC(grid);
    }

    private ParaWithDeadendSC buildParaWDe() {
        var pFuseState = getOrDefaultFuseStatus(ScenarioName.PARA_W_DEADEND.getNbFuses());
        var pConsumptions = getOrDefaultConsumptions(ScenarioName.PARA_W_DEADEND.getNbFuses()/2);

        var subs = new Substation(ParaWithDeadendSC.SUBSTATION_NAME);
        var cab1 = new Cabinet(ParaWithDeadendSC.CABINET1_NAME);

        createCable(ParaWithDeadendSC.F1_NAME, ParaWithDeadendSC.F2_NAME, pFuseState[0], pFuseState[1], subs, cab1, pConsumptions[0]);
        createCable(ParaWithDeadendSC.F3_NAME, ParaWithDeadendSC.F4_NAME, pFuseState[2], pFuseState[3], subs, cab1, pConsumptions[1]);

        var grid = new SmartGrid();
        grid.addSubstations(subs);
        return new ParaWithDeadendSC(grid);
    }


    private double[] getOrDefaultConsumptions(int nbCable) {
        if(consumptions == null) {
            consumptions = new double[nbCable];
        }
        checkConsumptionsParam(nbCable);

        return consumptions;
    }

    private State[] getOrDefaultFuseStatus(int nbFuses) {
        if(fuseState == null) {
            fuseState = new State[nbFuses];
            Arrays.fill(fuseState, State.CLOSED);
        } else {
            checkFuseStatusParam(nbFuses);
        }
        return fuseState;
    }


}
