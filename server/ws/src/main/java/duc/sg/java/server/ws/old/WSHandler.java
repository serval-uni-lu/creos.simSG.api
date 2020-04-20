package duc.sg.java.server.ws.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import duc.sg.java.server.ws.old.messages.ActionRequest;
import duc.sg.java.server.ws.old.messages.Error;
import duc.sg.java.server.ws.old.messages.Message;
import duc.sg.java.server.ws.old.messages.MessageType;
import duc.sg.java.server.ws.old.worker.SCBasedApprox;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.*;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WSHandler implements WebSocketConnectionCallback {
    public static final WSHandler INSTANCE = new WSHandler();

    private WSHandler() {}


    private String loadContract() throws IOException {
        var contractStream = WSHandler.class.getClassLoader().getResourceAsStream("contract.json");
        if (contractStream == null) {
            var root = WSHandler.class.getClassLoader().getResource("./").getFile();
            throw new RuntimeException("contract.json not found at " + root + "/contract.json");
        }

        var content = new StringBuilder();
        try(var reader = new BufferedReader(new InputStreamReader(contractStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        return content.toString();
    }

    @Override
    public void onConnect(WebSocketHttpExchange webSocketHttpExchange, WebSocketChannel wsChannel) {
        System.out.println("Client connected!!");
        try {
            WebSockets.sendText(loadContract(), wsChannel, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        wsChannel.getReceiveSetter().set(new AbstractReceiveListener() {
            @Override
            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                var msg = message.getData();
                System.out.println("Message receiced: " + msg);




                var jsonObj = JSON.parseObject(msg);
                MessageType messageType;
                try {
                    messageType = MessageType.valueOf(jsonObj.getString("messageType").toUpperCase());
                }catch (IllegalArgumentException ex) {
                    errorMsg(jsonObj, wsChannel, "messageType not defined in the request");
                    return;
                }

                switch (messageType) {
                    case REQUEST_ACTION: {
                        var actionRequest = JSON.parseObject(msg, ActionRequest.class);

                        Message result = SCBasedApprox.execute(actionRequest);

                        var textMessage = JSON.toJSONString(result);
                        WebSockets.sendText(textMessage, wsChannel, null);
                        break;
                    }
                    default: {
                        errorMsg(jsonObj, wsChannel, "messageType not handled by the actuator.");
                    }
                }

            }

            @Override
            protected void onClose(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                System.out.println("Client has closed the connection.");
            }
        });
        wsChannel.resumeReceives();
    }

    private void errorMsg(JSONObject jsonObj, WebSocketChannel wsChannel, String message) {
        var actionId = jsonObj.getLong("actionID");
        actionId = (actionId == null)? -1 : actionId;
        var executionId = jsonObj.getLong("executionID");
        executionId = (executionId == null)? -1 : executionId;
        var error = new Error(actionId, executionId,message);
        WebSockets.sendText(JSON.toJSONString(error), wsChannel, null);
    }
}
