package com.labs.dm.haselnuss.server.http;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.server.http.handlers.AdminHandler;
import com.labs.dm.haselnuss.server.http.handlers.MainHandler;
import com.labs.dm.haselnuss.server.http.handlers.RestHandler;
import com.labs.dm.haselnuss.utils.Utils;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author daniel
 */
public class RestServer {

    private static final Logger logger = Logger.getLogger(RestServer.class.getSimpleName());
    private HttpServer server;
    private int port;

    public RestServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        Haselnuss.newInstance().createRestServer(8081).start();
    }

    public void start() throws IOException {
        long time = System.currentTimeMillis();
        InetSocketAddress addr = new InetSocketAddress(port);
        server = HttpServer.create(addr, 0);
        server.createContext("/", new MainHandler());
        server.createContext("/admin", new AdminHandler());
        server.createContext("/rest", new RestHandler());
        server.createContext("/storage", new RestHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        logger.info("Server is listening on port " + port);
        logger.info("Usage: http://localhost:" + port + "/rest/storage/key");
        logger.info("Admin URL: http://localhost:" + port + "/admin");
        logger.info("PID: " + Utils.pid());
        logger.info("Server started in: " + (System.currentTimeMillis() - time) + " ms");

        if (Haselnuss.newInstance().getProperties().getProperty("http.start.browser").equals("Y")) {
            Utils.openWebpage(URI.create("http://localhost:" + port));
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }

    }

    public int getPort() {
        return port;
    }
}

