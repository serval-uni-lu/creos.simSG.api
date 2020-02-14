package duc.aintea.server;

import io.undertow.Handlers;
import io.undertow.Undertow;

import java.io.IOException;

public class RunServer {
    public static final int WS_PORT = 8585;


    public static void main(String[] args) throws IOException {
        final var server = Undertow.builder()
                .addHttpListener(WS_PORT, "localhost")
                .setHandler(Handlers.path().addPrefixPath("/ws", Handlers.websocket(WSHandler.INSTANCE)))
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }





}
