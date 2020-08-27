import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {ActionData} from "@/utils/actionUtils";
import LoadApprox from "@/plugin/action/loadApprox/LoadApprox";
import ULoadApprox from "@/plugin/action/uLoadApprox/ULoadApprox";


Vue.config.productionTip = false;

Vue.prototype.$actionCmp = Array<ActionData>();
Vue.use(LoadApprox);
Vue.use(ULoadApprox);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');


