package duc.sg.java.server.ws.message;

import duc.sg.java.server.ws.JsonLoaderException;

import java.util.logging.Logger;

public class MessageBuilder {
    private static final Logger LOGGER = Logger.getLogger(MessageBuilder.class.getName());

    private MessageBuilder(){}

    public static Message buildHelloMsg() {
        try {
            return new ActuatorHello();
        } catch (JsonLoaderException e) {
            LOGGER.throwing(MessageBuilder.class.getName(), "buildHelloMsg", e);
            return new Error("Error while loading the ");
        }
    }

}
