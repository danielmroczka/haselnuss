package com.labs.dm.haselnuss.server.http.handlers;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IFileStorage;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by daniel on 2015-05-23.
 */
public class RestHandler extends AbstractHttpHandler {

    private static final Logger logger = Logger.getLogger(RestHandler.class.getSimpleName());

    public RestHandler() {
    }

    @Override
    public void onGet(HttpExchange exchange) throws IOException {
        long time = System.currentTimeMillis();
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");

        try (OutputStream responseBody = exchange.getResponseBody()) {
            String[] paths = decodeURL(exchange);
            String storage = paths[1];
            String key = paths[2];
            Serializable val = Haselnuss.newInstance().createFileMapDatabase(storage).get(key);

            if (val != null) {
                exchange.sendResponseHeaders(200, 0);
                if (val instanceof String) {
                    byte[] b = ((String) val).getBytes();
                    responseBody.write(b);
                    logger.info("Read " + b.length + " bytes in " + (System.currentTimeMillis() - time) + " ms.");
                }
            } else {
                exchange.sendResponseHeaders(404, 0);
                responseBody.write("Error 404 - Not Found!".getBytes());
            }
        }
    }

    @Override
    public void onPost(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");

        String[] paths = decodeURL(exchange);
        String storage = paths[1];
        String key = paths[2];
        String value = decodeInputStream(exchange.getRequestBody());
        IFileStorage s = Haselnuss.newInstance().createFileMapDatabase(storage);
        s.put(key, value);
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write("POST OK".getBytes());
        }
        logger.info("Insert " + key + ", " + s.get(key) + " size:" + value.length() + " bytes");
        if (s != null) {
            s.flush();
        }
    }

    @Override
    public void onPut(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        String[] paths = decodeURL(exchange);
        String storage = paths[1];
        String key = paths[2];
        String value = decodeInputStream(exchange.getRequestBody());
        IFileStorage s = Haselnuss.newInstance().createFileMapDatabase(storage);
        s.set(key, value);
        exchange.sendResponseHeaders(200, 0);

        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write("PUT OK".getBytes());
        }

        s.flush();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.log(Level.INFO, "[{0}] Request: {1} Type: {2}", new Object[]{new Date(), exchange.getRequestURI(), exchange.getRequestMethod()});
        super.handle(exchange);
    }


}
