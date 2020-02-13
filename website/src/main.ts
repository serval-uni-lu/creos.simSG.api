import Vue from 'vue'
import App from './views/App.vue'
import router from './router'
import store from './store'


Vue.config.productionTip = false

let app = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')





let RQ_SAYS_ACT = "actuator-hello/"


let wsURL = 'ws://localhost:8585/ws';
var ws = new WebSocket(wsURL);
ws.onopen = function () {
  console.debug("WebSocket connected to " + wsURL)
};

ws.onmessage = function(event) {
  var message = event.data;
  if(message.startsWith(RQ_SAYS_ACT)) {
    message = message.substr(RQ_SAYS_ACT.length);
    message = JSON.parse(message);

    console.debug("[" + wsURL + "] Data received: ");
    console.debug(message);

    app.$store.commit('addActuator', message);

  }
  
};

ws.onclose = function(event) {
  console.debug("Server has closed the connection");
  console.log(event);
};


