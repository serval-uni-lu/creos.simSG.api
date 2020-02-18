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
    confidenceLevel: number;

    constructor(isClosed: boolean, load: number, confidenceLevel: number) {
        this.isClosed = isClosed;
        this.load = load;
        this.confidenceLevel = confidenceLevel;
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

const NullSelection = new Selection(-1, "null");

let idxGenerator = 0;

export default new Vuex.Store({
    state: {
        consumptions: Array<number>(),
        loads: Array<number>(),
        inspVisible: false,
        selectedElmt: NullSelection,
        fuses: Array<Fuse>(),
        cableLoads: Array<number>(),
        actuators: Array<Actuator>(),
        successMessage: "",
        errorMessage: ""

    },
    mutations: {
        init(state, nbFuses: number) {
            state.consumptions = new Array(nbFuses/2).fill(0.)
            state.loads = new Array(nbFuses/2).fill(-1)

            for(var i=0; i<nbFuses; i++) {
                state.fuses.push(new Fuse(true, -1, 100));
            }
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
            state.loads = [];
            for (const idx in loads) {
                Vue.set(state.loads, idx, loads[idx])
            }
        },
        setFuseLoads(state, loads:Array<number>) {
            for (const idx in loads) {
                var fuse = state.fuses[idx]
                fuse.load = loads[idx]
                Vue.set(state.fuses, idx, fuse)
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
    }
})