import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import Home from '../views/Home.vue'
import SCView from '../views/SCView.vue'

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/scenario',
    name: 'scenario',
    component: SCView
  },
  {
    path: '/scenario/:name',
    name: 'FirstRoute',
    // component: SCView,
    props: true
  },
  {
    path: '/scenario-builder',
    name: 'scenario-builder',
    component: () => import('../views/SCBuilder.vue')
    // component: ScenarioBuilder
  },
  {
    path: '/lux-sg',
    name: 'lux-sg',
    component: () => import('../views/LuxSG.vue')
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
