import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        fuseStatus: Array<boolean>(),
        consumptions: Array<number>(),
        currentMeterId: 0,
    },
    mutations: {
        init(state, nbFuses) {
            state.fuseStatus = new Array(nbFuses).fill(true)
            state.consumptions = new Array(nbFuses/2).fill(0.)
        }, 
        switchFuse(state, fuseId) {
            console.log("I am called!")
            Vue.set(state.fuseStatus, fuseId, !state.fuseStatus[fuseId])
        }
    }
})