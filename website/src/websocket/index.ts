import {app} from '../main'

let RQ_SAYS_ACT = "actuator-hello/"

let timeOut: number;
let wsURL = 'ws://localhost:8585/ws';
let ws: WebSocket | null;

function onOpen() {
    clearTimeout(timeOut);
    console.debug("WebSocket connected to " + wsURL)
}

function onMessage(event: any) {
    var message = event.data;
    if(message.startsWith(RQ_SAYS_ACT)) {
        message = message.substr(RQ_SAYS_ACT.length);
        message = JSON.parse(message);

        console.debug("[" + wsURL + "] Data received: ");
        console.debug(message);

        app.$store.commit('addActuator', message);

    }
}

function onClose(event: any) {
    console.debug("Server has closed the connection");
    console.log(event);
    ws = null;
}

function connect() {
    console.debug("Connection attempt to " + wsURL)
    if(ws !== null && ws !== undefined) {
        ws.close();
        ws = null;
    }
    ws = new WebSocket(wsURL);
    ws.onopen = onOpen;
    ws.onmessage = onMessage;
    ws.onclose = onClose;
    timeOut = setTimeout(function() {   
        if(ws !== null) ws.close();     
        connect();
    },  5000);
}

export default {
    init: function() {
       connect();      
    }
}