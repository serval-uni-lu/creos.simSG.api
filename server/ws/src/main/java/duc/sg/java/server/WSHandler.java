package duc.sg.java.server;

import duc.sg.java.server.message.ActionListMsg;
import duc.sg.java.server.message.Message;
import duc.sg.java.server.message.MessageProcessor;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.*;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WSHandler implements WebSocketConnectionCallback {
    private static final Logger LOGGER = Logger.getLogger(WSHandler.class.getName());
    static {
        LOGGER.setLevel(Level.INFO);
    }

    private void sendMessage(WebSocketChannel wsChannel, Message msg) {
        if(wsChannel == null || !wsChannel.isOpen()) {
            LOGGER.severe("Error while sending the message: channel closed or not initialised.");
        }

        String msgStr = msg.toJson();
        WebSockets.sendText(msgStr, wsChannel, new WebSocketCallback<Void>() {
            @Override
            public void complete(WebSocketChannel channel, Void context) {
                LOGGER.info("Message successfully send");
            }

            @Override
            public void onError(WebSocketChannel channel, Void context, Throwable throwable) {
                LOGGER.severe("Error while sending the message: " + throwable.getMessage());
                LOGGER.throwing(old.duc.sg.java.server.ws.WSHandler.class.getName(), "sendMessage", throwable);
            }
        });
    }

    @Override
    public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
        LOGGER.info("Client connected");

        sendMessage(channel, new ActionListMsg("LoadApproxVue", "ULoadApproxVue"));

        channel.getReceiveSetter().set(new AbstractReceiveListener() {
            @Override
            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                var msg = message.getData();
                LOGGER.info("Message received: " + msg);
                Optional<Message> answer = MessageProcessor.process(msg);
                if(answer.isPresent()) {
                    sendMessage(channel, answer.get());
                } else {
                    LOGGER.info("Message process is empty. No answer to send");
                }
            }
        });
        channel.resumeReceives();


    }
}
