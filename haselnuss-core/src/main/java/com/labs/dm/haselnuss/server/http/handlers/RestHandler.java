package com.labs.dm.haselnuss.server.http.handlers;

import com.labs.dm.haselnuss.core.IStorage;
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

    private static final Logger logger = Logger.getAnonymousLogger();
    private final IStorage storage;

    public RestHandler(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void onGet(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");

        try (OutputStream responseBody = exchange.getResponseBody()) {
            String path = exchange.getRequestURI().getPath();
            path = path.substring(exchange.getHttpContext().getPath().length() + 1);
            Serializable val = storage.get(path);

            if (val != null) {
                exchange.sendResponseHeaders(200, 0);
            } else {
                exchange.sendResponseHeaders(404, 0);
            }

            if (val instanceof String) {
                byte[] b = ((String) val).getBytes();
                responseBody.write(b);
            }

        }
    }

    @Override
    public void onPost(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, 0);
        String path = exchange.getRequestURI().getPath();
        path = path.substring(exchange.getHttpContext().getPath().length() + 1);

        storage.put(path, new Date().toString());

        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write("".getBytes());
        }
    }

    @Override
    public void onPut(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, 0);
        String path = exchange.getRequestURI().getPath();
        path = path.substring(exchange.getHttpContext().getPath().length() + 1);

        storage.set(path, new Date().toString());

        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write("".getBytes());
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.log(Level.INFO, "[{0}] Request: {1}", new Object[]{new Date(), exchange.getRequestURI()});
        super.handle(exchange);
    }
}
