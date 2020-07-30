import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter);

//   const routes: Array<RouteConfig> = [
//   {
//     path: '/',
//     name: 'Home',
//     component: Home
//   },
//   {
//     path: '/about',
//     name: 'About',
//     // route level code-splitting
//     // this generates a separate chunk (about.[hash].js) for this route
//     // which is lazy-loaded when the route is visited.
//     component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
//   }
// ];

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/scenario',
    name: 'scenario',
    // component: Scenarios
  },
  {
    path: '/scenario/:name',
    name: 'FirstRoute',
    // component: ScenarioView,
    props: true
  },
  {
    path: '/scenario-builder',
    name: 'scenario-builder',
    // component: ScenarioBuilder
  },
  {
    path: '/lux-sg',
    name: 'lux-sg',
    // component: LuxembourgSG
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
