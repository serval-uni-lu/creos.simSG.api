import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        fuseStatus: Array<boolean>(),
        consumptions: Array<number>(),
        inspVisible: false,
        currentMeterId: 0,
        selectedMeter: -1
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
        }
    }
})