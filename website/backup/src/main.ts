import Vue from 'vue'
import App from './views/App.vue'
import router from './router'
import store from './store'
import WS from "./websocket"


Vue.config.productionTip = false

let app = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

export { app };

WS.init();








