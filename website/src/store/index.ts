import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        fuseStatus: Array<boolean>(),
        consumptions: Array<number>(),
        inspVisible: false,
    },
    mutations: {
        init(state, nbFuses) {
            state.fuseStatus = new Array(nbFuses).fill(true)
            state.consumptions = new Array(nbFuses/2).fill(0.)
        }, 
        reset(state) {
            state.fuseStatus = [];
            state.consumptions = [];
        },
        switchFuse(state, fuseId) {
            Vue.set(state.fuseStatus, fuseId, !state.fuseStatus[fuseId])
        },
        hideInspector(state) {
            state.inspVisible = false;
        },
        showInspector(state) {
            state.inspVisible = true;
        }
    }
})