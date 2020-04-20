package duc.sg.java.server.ws;

import duc.sg.java.server.ws.message.Message;
import duc.sg.java.server.ws.message.MessageBuilder;
import duc.sg.java.server.ws.worker.MessageProcessor;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.*;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WSHandler implements WebSocketConnectionCallback {
    private static final Logger LOGGER = Logger.getLogger(WSHandler.class.getName());
    static {
        LOGGER.setLevel(Level.INFO);
    }

    public WSHandler() {}

    private void sendMessage(WebSocketChannel wsChannel, Message msg) throws WSException {
        if(wsChannel == null || !wsChannel.isOpen()) {
            throw new WSException("Channel hos not been initialised");
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
                LOGGER.throwing(WSHandler.class.getName(), "sendMessage", throwable);
            }
        });

    }


    @Override
    public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
        LOGGER.info("Client connected.");
        Message msg = MessageBuilder.buildHelloMsg();
        try {
            sendMessage(channel, msg);
        } catch (WSException e) {
            LOGGER.throwing(WSHandler.class.getName(), "onConnect", e);
            return;
        }
        channel.getReceiveSetter().set(new AbstractReceiveListener() {
            @Override
            protected void onClose(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                LOGGER.info("Connection close");
            }

            @Override
            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                var msg = message.getData();
                LOGGER.info("Message received: " + msg);
                Message answer = MessageProcessor.process(msg);
                try {
                    sendMessage(channel, answer);
                } catch (WSException e) {
                    LOGGER.throwing(WSHandler.class.getName(), "onConnect", e);
                }
            }
        });
        channel.resumeReceives();
    }


    private static class WSException extends Exception {
        public WSException(String message) {
            super(message);
        }
    }

}
