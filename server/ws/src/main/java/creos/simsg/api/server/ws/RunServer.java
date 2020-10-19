package creos.simsg.api.server.ws;

import io.undertow.Handlers;
import io.undertow.Undertow;

public class RunServer {
    public static final int PORT = 8585;

    public static void main(String[] args) {
        final var server = Undertow.builder()
            .addHttpListener(PORT, "localhost")
            .setHandler(Handlers.path().addPrefixPath("/ws", Handlers.websocket(new WSHandler())))
            .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

}
