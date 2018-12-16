package com.docker.java12;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class PingPong {

    //<editor-fold defaultstate="collapsed" desc="HTTP Handler for Ping handler">
    private static class PingHandler implements HttpHandler {

        @Override
        public void handle(final HttpExchange t) throws IOException {
            final String response = "pong\n";
            t.sendResponseHeaders(200, response.length());
            try (final OutputStream os = t.getResponseBody()) {
                os.write(response.getBytes(Charset.defaultCharset()));
            }
        }
    }
    //</editor-fold>

    protected final static short PORT = 8082;
    private HttpServer server;

    protected void stop() {
        server.stop(1);
    }

    protected void start() {
        try {

            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/ping", new PingHandler());
            server.setExecutor(null);
            server.start();

            final String msg = String.format(
                    "PingPong service started on port %s. Initiate a HTTP call with CURL:%ncurl http://localhost:8082/ping",
                    PORT
            );
            Logger.getLogger(PingPong.class.getName()).log(Level.INFO, msg);

        } catch (final IOException e) {
            Logger.getLogger(PingPong.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(final String[] args) {
        new PingPong().start();
    }
}
