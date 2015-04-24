package com.labs.dm.jkvdb.server;

import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.SimpleFileMapStorage;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 *
 * @author daniel
 */
public class HttpServerDemo {

    final IStorage storage = new SimpleFileMapStorage("rest");

    public void start() throws IOException {
        storage.put("key", "Hello World!");
        storage.flush();

        InetSocketAddress addr = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(addr, 0);

        server.createContext("/", new MyHandler());
        server.createContext("/rest", new RestHandler(storage));
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 8080");
    }

}

class RestHandler implements HttpHandler {

    private final IStorage storage;

    RestHandler(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(requestMethod)) {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");

            try (OutputStream responseBody = exchange.getResponseBody()) {
                String path = exchange.getRequestURI().getPath();//.get.getPath();
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
        } else if ("POST".equalsIgnoreCase(requestMethod)) {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);
            String path = exchange.getRequestURI().getPath();//.get.getPath();
            path = path.substring(exchange.getHttpContext().getPath().length() + 1);

            storage.put(path, new Date().toString());

            try (OutputStream responseBody = exchange.getResponseBody()) {
                responseBody.write("".getBytes());
            }

        } else if ("PUT".equalsIgnoreCase(requestMethod)) {
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
    }
}

class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("GET")) {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);

            try (OutputStream responseBody = exchange.getResponseBody()) {
                Headers requestHeaders = exchange.getRequestHeaders();
                Set<String> keySet = requestHeaders.keySet();
                Iterator<String> iter = keySet.iterator();
                while (iter.hasNext()) {
                    String key = iter.next();
                    List values = requestHeaders.get(key);
                    String s = key + " = " + values.toString() + "\n";
                    responseBody.write(s.getBytes());
                }
            }
        }
    }
}
