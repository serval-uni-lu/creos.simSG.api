package creos.simsg.api.server.ws.message;

/**
 * Interface that should be implemented by message that request information or result from the server.
 */
public interface RequestMessage {
    Message process();
}
