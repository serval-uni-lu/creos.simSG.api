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

let runningActions = new Map<number, Action>();
let idxGenerator = 0;

export default new Vuex.Store({
    state: {
        fuseStatus: Array<boolean>(),
        consumptions: Array<number>(),
        inspVisible: false,
        currentMeterId: 0,
        selectedMeter: -1,
        // isApproximating: false,
        // showAlertApprox: false,
        actuators: Array<Actuator>()

    },
    mutations: {
        init(state, nbFuses) {
            state.fuseStatus = new Array(nbFuses).fill(true)
            state.consumptions = new Array(nbFuses/2).fill(0.)
        },
        switchFuse(state, fuseId) {
            Vue.set(state.fuseStatus, fuseId, !state.fuseStatus[fuseId])
        },
        hideInspector(state) {
            state.inspVisible = false;
            state.selectedMeter = -1;
        },
        showInspector(state, meterId) {
            state.currentMeterId = meterId;
            state.inspVisible = true;
            state.selectedMeter = meterId;
        },
        startAction(state, {scenarioID, action}: {scenarioID:number, action: Action}) {
            runningActions.set(idxGenerator, action)
            var msg = {
                actionID: idxGenerator,
                scenario: scenarioID,
                fuseStates: state.fuseStatus,
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
        }
    },
    actions: {
        runAction (context, {actName, acts} ) {
           
        }
    }
})