import Vue from 'vue'
import VueRouter from 'vue-router'
import LuxembourgSG from '../views/LuxembourgGrid.vue'
import Scenarios from '../views/Scenario.vue'
import ScenarioBuilder from '../views/ScenarioBuilder.vue'
import Home from '../views/Home.vue'
import ScenarioTest from '../components/ScenarioTest.vue'

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
        path: '/scenario-builder',
        name: 'scenario-builder',
        component: ScenarioBuilder
    },
    {
        path: '/lux-sg',
        name: 'lux-sg',
        component: LuxembourgSG
    },
    {
        path: '/scenario/:name',
        name: 'FirstRoute',
        component: ScenarioTest
      }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router