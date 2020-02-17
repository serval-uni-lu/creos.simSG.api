import {app} from '../main'

let MSG_SAYS_ACT = "actuator_hello"
let MSG_RES_ACT = "action_result"
let MSG_RQ_ACT = "request_action"
let MSG_ERROR = "error"



let timeOut: number;
let wsURL = 'ws://localhost:8585/ws';
let ws: WebSocket | null;

function onOpen() {
    clearTimeout(timeOut);
    console.debug("WebSocket connected to " + wsURL)
}

function debugReceivedMessage(message: any) {
    console.debug("[" + wsURL + "] Data received: ");
    console.debug(message);
}

function onMessage(event: any) {
    var message;
    try {
        message = JSON.parse(event.data);
    }catch(parseErr) {
        console.error("Wrongly formatted message received (not a valid JSON): " + parseErr);
        console.error(event);
    }

    if(message.messageType === undefined) {
        console.error("Message received without a message type: ");
        console.error(message)
    } else if(message.messageType === MSG_SAYS_ACT) {
        debugReceivedMessage(message);
        app.$store.commit('addActuator', message);
    } else if(message.messageType === MSG_RES_ACT) {
        debugReceivedMessage(message);
        app.$store.commit('setCableLoads', message.cableLoads)
        app.$store.commit('setFuseLoads', message.fuseLoads)

        app.$store.commit('setSuccessMessage', "Accession executed with success.");

        setTimeout(function(){app.$store.commit('removeSuccessMessage')}, 5000)

    } else if(message.messageType === MSG_ERROR) {
        debugReceivedMessage(message);
    } else {
        console.error("Message with an expected type received: " + message.messageType);
        console.error(message)
    }
}

function onClose(event: CloseEvent) {
    console.debug("Connection has been closed. [" + event.code + "]: " + event.reason);
    ws = null;
    app.$store.commit('removeActuator')
}

function connect() {
    console.debug("Connection attempt to " + wsURL)
    if(ws !== null && ws !== undefined) {
        ws.close(4000, "Server not connected");
        ws = null;
    }
    ws = new WebSocket(wsURL);
    ws.onopen = onOpen;
    ws.onmessage = onMessage;
    ws.onclose = onClose;
    timeOut = setTimeout(function() {   
        if(ws !== null) ws.close(4000, "Server not connected");     
        connect();
    },  5000);
}

export default {
    init: function() {
       connect();      
    },
    sendActionRequest(message: any) {
        if(ws !== null && ws.readyState === WebSocket.OPEN) {
            message.messageType = MSG_RQ_ACT;
            var msgText = JSON.stringify(message);
            console.debug("Message sent to actuator: " + msgText)
            ws.send(msgText);
        }
    }
}