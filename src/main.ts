import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {ActionData} from "@/utils/actionUtils";
import MockAction from "@/plugin/action/MockAction";


Vue.config.productionTip = false;

Vue.prototype.$actionCmp = Array<ActionData>();
Vue.use(MockAction as any);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');


