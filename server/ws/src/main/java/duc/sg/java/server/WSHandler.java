package duc.sg.java.server;

import duc.sg.java.server.message.Message;
import duc.sg.java.server.message.MessageFactory;
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

    @Override
    public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
        LOGGER.info("Client connected");

        welcome(channel);

        channel.getReceiveSetter().set(new AbstractReceiveListener() {
            @Override
            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                var msg = message.getData();
                LOGGER.info("Message received: " + msg);
                Optional<Message> msp = MessageProcessor.process(msg);

                msp.ifPresent((Message m) -> System.out.println(m.toJson()));

            }
        });
        channel.resumeReceives();


    }

    private void welcome(WebSocketChannel channel) {
        Optional<String> welcomeMsg = MessageFactory
                .buildActionListMessage("LoadApproxVue");

        welcomeMsg.ifPresent((String s) -> WebSockets.sendText(s, channel, new WebSocketCallback<>() {
            @Override
            public void complete(WebSocketChannel channel, Void context) {
                LOGGER.info("Message successfully sent: " + s);
            }

            @Override
            public void onError(WebSocketChannel channel, Void context, Throwable throwable) {
                LOGGER.severe("Error while send the message: " + throwable.getMessage());
                LOGGER.throwing(WSHandler.class.getName(), "onConnect", throwable);
            }
        }));
    }
}
