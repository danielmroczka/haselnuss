package com.labs.dm.haselnuss.server.http.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by daniel on 2015-05-30.
 */
public class CustomAbstractHttpHandler extends AbstractHttpHandler {

    private final String page;

    public CustomAbstractHttpHandler(String page) {
        this.page = page;
    }

    @Override
    public void onGet(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        renderPage(exchange, page);
    }
}
