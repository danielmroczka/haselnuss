package com.labs.dm.jkvdb.server.http;

import com.labs.dm.jkvdb.Utils;
import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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
public class HttpServerDemo
{

    private HttpServer server;


    public static void main(String[] args) throws IOException
    {
        new HttpServerDemo().start();
    }

    final IFileStorage storage = new SimpleFileMapStorage("rest");

    public void start() throws IOException
    {
        storage.put("key", "Hello World!");
        storage.flush();

        InetSocketAddress addr = new InetSocketAddress(8080);
        server = HttpServer.create(addr, 0);

        server.createContext("/", new MyHandler());
        server.createContext("/admin", new AdminHandler());
        server.createContext("/rest", new RestHandler(storage));
        server.createContext("/storage", new RestHandler(storage));
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 8080");
        System.out.println("Usage: http://localhost:8080/rest/key");
        System.out.println("PID: " + Utils.pid());
    }

    public void stop()
    {
        if (server != null)
        {
            server.stop(0);
        }
    }

}

class RestHandler implements HttpHandler
{

    static IStorage storage;
    private static Logger logger = Logger.getAnonymousLogger();

    RestHandler(IStorage storage)
    {
        this.storage = storage;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        System.out.println("[" + new Date() + "] Request: " + exchange.getRequestURI());
        String requestMethod = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(requestMethod))
        {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");

            try (OutputStream responseBody = exchange.getResponseBody())
            {
                String path = exchange.getRequestURI().getPath();//.get.getPath();
                path = path.substring(exchange.getHttpContext().getPath().length() + 1);
                Serializable val = storage.get(path);

                if (val != null)
                {
                    exchange.sendResponseHeaders(200, 0);
                }
                else
                {
                    exchange.sendResponseHeaders(404, 0);
                }

                if (val instanceof String)
                {
                    byte[] b = ((String) val).getBytes();
                    responseBody.write(b);
                }

            }
        }
        else if ("POST".equalsIgnoreCase(requestMethod))
        {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);
            String path = exchange.getRequestURI().getPath();//.get.getPath();
            path = path.substring(exchange.getHttpContext().getPath().length() + 1);

            storage.put(path, new Date().toString());

            try (OutputStream responseBody = exchange.getResponseBody())
            {
                responseBody.write("".getBytes());
            }

        }
        else if ("PUT".equalsIgnoreCase(requestMethod))
        {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);
            String path = exchange.getRequestURI().getPath();
            path = path.substring(exchange.getHttpContext().getPath().length() + 1);

            storage.set(path, new Date().toString());

            try (OutputStream responseBody = exchange.getResponseBody())
            {
                responseBody.write("".getBytes());
            }
        }
    }
}

class MyHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("GET"))
        {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);

            try (OutputStream responseBody = exchange.getResponseBody())
            {
                Headers requestHeaders = exchange.getRequestHeaders();
                Set<String> keySet = requestHeaders.keySet();
                for (String key : keySet)
                {
                    List values = requestHeaders.get(key);
                    String s = key + " = " + values.toString() + "\n";
                    responseBody.write(s.getBytes());
                }
            }
        }
    }

}

class AdminHandler implements HttpHandler
{
    Logger logger = Logger.getAnonymousLogger();
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        exchange.sendResponseHeaders(200, 0);
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/html");
        try (OutputStream responseBody = exchange.getResponseBody())
        {

            java.net.URL url = this.getClass().getResource("/web/admin.html");
            System.out.println(url.toString());
            java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
            String html = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");

            //Path path = Paths.get("admin.html");
            //String html = Files.readAllLines(path).toString();
            System.out.println(html);

            responseBody.write(html.getBytes());
        } catch (Exception e)
        {
            logger.log(Level.SEVERE, null, e);
        }
    }

}
