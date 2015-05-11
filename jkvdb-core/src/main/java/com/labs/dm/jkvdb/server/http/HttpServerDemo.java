package com.labs.dm.jkvdb.server.http;

import com.labs.dm.jkvdb.Utils;
import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author daniel
 */
public class HttpServerDemo {
    
    private static final Logger logger = Logger.getLogger(HttpServerDemo.class.getSimpleName());
    private final IFileStorage storage = new SimpleFileMapStorage("rest");
    private HttpServer server;
    
    public static void main(String[] args) throws IOException {
        new HttpServerDemo().start();
    }
    
    public void start() throws IOException {
        storage.put("key", "Hello World!");
        storage.flush();
        
        InetSocketAddress addr = new InetSocketAddress(8081);
        server = HttpServer.create(addr, 0);
        
        server.createContext("/", new MyHandler());
        server.createContext("/admin", new AdminHandler());
        server.createContext("/rest", new RestHandler(storage));
        server.createContext("/storage", new RestHandler(storage));
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        
        logger.info("Server is listening on port 8080");
        logger.info("Usage: http://localhost:8080/rest/key");
        logger.log(Level.INFO, "PID: {0}", Utils.pid());
    }
    
    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }
    
}

class RestHandler extends AbstractHttpHandler {
    
    private static final Logger logger = Logger.getAnonymousLogger();
    private final IStorage storage;

    RestHandler(IStorage storage) {
        this.storage = storage;
    }
    
    @Override
    void onGet(HttpExchange exchange) throws IOException {
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
    void onPost(HttpExchange exchange) throws IOException {
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
    void onPut(HttpExchange exchange) throws IOException {
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

class MyHandler extends AbstractHttpHandler {
    
    @Override
    void onGet(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, 0);
        
        try (OutputStream responseBody = exchange.getResponseBody()) {
            Headers requestHeaders = exchange.getRequestHeaders();
            Set<String> keySet = requestHeaders.keySet();
            for (String key : keySet) {
                List values = requestHeaders.get(key);
                String s = key + " = " + values.toString() + "\n";
                responseBody.write(s.getBytes());
            }
        }
    }
    
}

class AdminHandler extends AbstractHttpHandler {
    
    static final Logger logger = Logger.getAnonymousLogger();
    
    @Override
    void onGet(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        renderPage(exchange, "admin");
    }
    
}
