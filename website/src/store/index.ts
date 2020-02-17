import Vue from 'vue'
import Vuex from 'vuex'
import WS from '../websocket'

Vue.use(Vuex)



interface Action {
    name: string,
    isBlocking: boolean
}

interface Actuator {
    name: string;
    author: string;
    actions: Array<Action>;
}

class Fuse {
    isClosed: boolean;
    load: number;

    constructor(isClosed: boolean, load: number) {
        this.isClosed = isClosed;
        this.load = load;
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

let runningActions = new Map<number, Action>();
let idxGenerator = 0;

export default new Vuex.Store({
    state: {
        consumptions: Array<number>(),
        loads: Array<number>(),
        inspVisible: false,
        selectedElmt: NullSelection,
        fuses: Array<Fuse>(),
        cableLoads: Array<number>(),
        // isApproximating: false,
        // showAlertApprox: false,
        actuators: Array<Actuator>()

    },
    mutations: {
        init(state, nbFuses) {
            state.consumptions = new Array(nbFuses/2).fill(0.)
            state.loads = new Array(nbFuses/2).fill(-1)

            for(var i=0; i<nbFuses; i++) {
                state.fuses.push(new Fuse(true, -1));
            }
        },
        switchFuse(state, fuseId) {
            var currF = state.fuses[fuseId];
            Vue.set(state.fuses, fuseId, new Fuse(!currF.isClosed, currF.load))

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
            runningActions.set(idxGenerator, action)

            var fuseStatus = new Array<boolean>();
            for (const fuse of state.fuses) {
                fuseStatus.push(fuse.isClosed)
            }

            var msg = {
                actionID: idxGenerator,
                scenario: scenarioID,
                fuseStates: fuseStatus,
                consumptions: state.consumptions
            };
            idxGenerator++;
            WS.sendActionRequest(msg);
        },
        // startApproximation(state) {
        //     state.isApproximating = true;
        //     state.showAlertApprox = true;
        //     console.log("Start approximation: " + (state.isApproximating && state.showAlertApprox))
        // },
        // stopApproximation(state) {
        //     state.isApproximating = false;
        //     state.showAlertApprox = false;
        //     console.log("Stop approximation: " + (state.isApproximating && state.showAlertApprox))
        // },
        // openAlertApproximation(state) {
        //     state.showAlertApprox = true;
        //     console.log("Open Alert: " + (state.isApproximating && state.showAlertApprox))
        // },
        // closeAlertApproximation(state) {
        //     state.showAlertApprox = false;
        //     console.log("Hide Alert: " + (state.isApproximating && state.showAlertApprox))
        // },
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
        }
    },
    actions: {
        runAction (context, {actName, acts} ) {
           
        }
    }
})