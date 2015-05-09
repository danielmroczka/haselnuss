package com.labs.dm.jkvdb.server.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

/**
 *
 * @author daniel
 */
public abstract class AbstractHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        String requestMethod = he.getRequestMethod();
        if (requestMethod != null) {
            switch (requestMethod.toLowerCase()) {
                case "get": {
                    onGet(he);
                    break;
                }

                case "post": {
                    onPost(he);
                    break;
                }

                default: {
                    throw new IllegalArgumentException("Unsupported request type");
                }
            }
        }
    }

    void onGet(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException();
    }

    void onPost(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException();
    }

    void onPut(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException();
    }

}
