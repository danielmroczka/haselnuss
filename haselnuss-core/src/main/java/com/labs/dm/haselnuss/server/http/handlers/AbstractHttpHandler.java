package com.labs.dm.haselnuss.server.http.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public abstract class AbstractHttpHandler implements HttpHandler {

    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public void handle(HttpExchange he) throws IOException {
        String requestMethod = he.getRequestMethod();
        if (requestMethod != null) {
            switch (requestMethod.toLowerCase()) {
                case "get":
                    onGet(he);
                    break;

                case "post":
                    onPost(he);
                    break;

                case "put":
                    onPut(he);
                    break;

                case "delete":
                    onDelete(he);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported request type");

            }
        }
    }

    public void onGet(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void onPost(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void onPut(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void onDelete(HttpExchange he) {
        throw new UnsupportedOperationException();
    }

    public void renderPage(HttpExchange exchange, String resource) {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/html");

        try (OutputStream responseBody = exchange.getResponseBody()) {

            URL url = this.getClass().getResource(getPrefix() + resource + getSuffix());
            Path resPath = Paths.get(url.toURI());
            String html = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
            responseBody.write(html.getBytes());
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    private String getPrefix() {
        return "/web/";
    }

    private String getSuffix() {
        return ".html";
    }

}
