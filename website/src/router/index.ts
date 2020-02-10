import Vue from 'vue'
import VueRouter from 'vue-router'
import LuxembourgSG from '../components/LuxembourgGrid.vue'
import Scenarios from '../components/Scenario.vue'
import ScenarioBuilder from '../components/ScenarioBuilder.vue'
import Home from '../components/Home.vue'
import ScenarioTest from '../components/ScenarioView.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/scenario',
        name: 'scenario',
        component: Scenarios
    },
    {
        path: '/scenario/:name',
        name: 'FirstRoute',
        component: ScenarioTest,
        props: true
    },
    {
        path: '/scenario-builder',
        name: 'scenario-builder',
        component: ScenarioBuilder
    },
    {
        path: '/lux-sg',
        name: 'lux-sg',
        component: LuxembourgSG
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router