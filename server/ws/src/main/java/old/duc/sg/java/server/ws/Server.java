package old.duc.sg.java.server.ws;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;

public class Server {
    public static final int WS_PORT = 8585;


    public static void main(String[] args) {
        final HttpHandler wsHandler = Handlers.path()
                .addPrefixPath("/ws", Handlers.websocket(new WSHandler()));


        final Undertow server = Undertow.builder()
                .addHttpListener(WS_PORT, "localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("/ws", wsHandler)
                )
                .build();
        server.start();
        Runtime.getRuntime()
                .addShutdownHook(new Thread(server::stop));
    }

}
