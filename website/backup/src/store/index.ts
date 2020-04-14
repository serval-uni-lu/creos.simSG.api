import Vue from 'vue'
import Vuex from 'vuex'
import WS from '../websocket'

Vue.use(Vuex)

interface Action {
    id: number,
    name: string,
    type: String
}

interface Actuator {
    name: string;
    author: string;
    actions: Array<Action>;
}

class Fuse {
    isClosed: boolean;
    load: number;
    uLoad: Array<Possibility>;
    confidenceLevel: number;

    constructor(isClosed: boolean, load: number, confidenceLevel: number) {
        this.isClosed = isClosed;
        this.load = load;
        this.confidenceLevel = confidenceLevel;
        this.uLoad = Array<Possibility>();
    }
}

class Selection {
    id: number;
    type: string;

    constructor(id: number, type: string) {
        this.id = id;
        this.type=type;
    }

    isSameAs(elemtId: number, elemtType: string): boolean {
        return this.id === elemtId && this.type === elemtType;
    }
}

class Possibility {
    value: number;
    confidence: number;

    constructor(value: number, confidence: number) {
        this.value = value;
        this.confidence = confidence;
    }
}

const NullSelection = new Selection(-1, "null");

let idxGenerator = 0;

export default new Vuex.Store({
    state: {
        consumptions: Array<number>(),
        inspVisible: false,
        // infoOverLayerVis: false,
        infoCblVis: false,
        infoFuseVis: false,
        selectedElmt: NullSelection,
        fuses: Array<Fuse>(),
        cableLoads: Array<number>(),
        uCableLoads: Array<Array<Possibility>>(),
        actuators: Array<Actuator>(),
        successMessage: "",
        errorMessage: "",
    },
    mutations: {
        init(state, nbFuses: number) {
            state.consumptions = new Array(nbFuses/2).fill(0.)
            state.cableLoads = new Array(nbFuses/2).fill(-1)
            state.fuses = Array<Fuse>();

            for(var i=0; i<nbFuses; i++) {
                state.fuses.push(new Fuse(true, -1, 100));
            }

            state.inspVisible = false;
            state.selectedElmt = NullSelection;
            state.cableLoads = new Array<number>();
        },
        switchFuse(state, fuseId: number) {
            var currF = state.fuses[fuseId];
            Vue.set(state.fuses, fuseId, new Fuse(!currF.isClosed, currF.load, currF.confidenceLevel))

        },
        hideInspector(state) {
            state.inspVisible = false;
            state.selectedElmt = NullSelection;
        },
        showInspector(state, {elemtId, elemtType}: {elemtId: number, elemtType: string}) {
            state.inspVisible = true;
            state.selectedElmt = new Selection(elemtId, elemtType)
        },
        startAction(state, {scenarioID, action}: {scenarioID:number, action: Action}) {
            console.debug("Start action: ")
            console.debug(action)
            
            if(action.type === 'sc-based') {
                var fuseStatus = new Array<boolean>();
                var fuseConfidence = new Array<number>();
                for (const fuse of state.fuses) {
                    fuseStatus.push(fuse.isClosed);
                    fuseConfidence.push(fuse.confidenceLevel);
                }

                var msg = {
                    actionID: action.id,
                    executionID: idxGenerator,
                    scenario: scenarioID,
                    fuseStates: fuseStatus,
                    fuseConfidence: fuseConfidence,
                    consumptions: state.consumptions
                };
                idxGenerator++;
                WS.sendActionRequest(msg);
            } else {
                console.error("Action type not supported by the client.")
            }
        },
        addActuator(state, toAdd: Actuator) {
            state.actuators.push(toAdd);
        },
        removeActuator(state) {
            state.actuators.pop();
        },
        setCableLoads(state, loads: Array<number>) {
            state.cableLoads = [];
            for (const idx in loads) {
                Vue.set(state.cableLoads, idx, loads[idx])
            }
        },
        setUCableLoads(state, {uCableLoads, uCableConf}: {uCableLoads: Array<Array<number>>, uCableConf: Array<Array<number>>}) {
            state.uCableLoads = Array<Array<Possibility>>();

            for(const idxCables in uCableLoads) {
                let cableLoads = uCableLoads[idxCables];
                let cableConfs = uCableConf[idxCables];

                let cablePoss = Array<Possibility>();
                
                for(const idxLoads in cableLoads) {
                    cablePoss.push(new Possibility(cableLoads[idxLoads],cableConfs[idxLoads]));
                }
                Vue.set(state.uCableLoads, idxCables, cablePoss);
            }
        },
        setFuseLoads(state, loads:Array<number>) {
            for (const idx in loads) {
                var fuse = state.fuses[idx]
                fuse.load = loads[idx]
                Vue.set(state.fuses, idx, fuse)
            }
        },
        setUFuseLoads(state, {uFuseLoads, uFuseConf}: {uFuseLoads: Array<Array<number>>, uFuseConf: Array<Array<number>>}) {
            for (const idx in uFuseLoads) {
                let fuseLoads = uFuseLoads[idx]
                let confs = uFuseConf[idx]

                let poss = Array<Possibility>();
                for(const idxLoad in fuseLoads) {
                    poss.push(new Possibility(fuseLoads[idxLoad], confs[idxLoad]));
                }

                var fuse = {
                    ...state.fuses[idx],
                    uLoad: poss,
                }

                Vue.set(state.fuses, idx, fuse );
            }
        },
        setSuccessMessage(state, msg: string) {
            state.successMessage = msg;
        },
        removeSuccessMessage(state) {
            state.successMessage = "";
        },
        setErrorMessage(state, msg: string) {
            state.errorMessage = msg;
        },
        removeErrorMessage(state) {
            state.errorMessage = "";
        },
        showHideInfoOL(state) {
            if(state.infoCblVis && state.infoFuseVis) {
                state.infoCblVis = false;
                state.infoFuseVis = false;
            } else {
                state.infoCblVis = true;
                state.infoFuseVis = true;
            } 
            // state.infoOverLayerVis = !state.infoOverLayerVis;
        },
        showHideInfoCable(state) {
            state.infoCblVis = !state.infoCblVis;
        },
        showHideInfoFuse(state) {
            state.infoFuseVis = !state.infoFuseVis;
        }
    }
})