package com.labs.dm.haselnuss.server.http.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by daniel on 2015-05-23.
 */
public class MainHandler extends AbstractHttpHandler {

    @Override
    public void onGet(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        renderPage(exchange, "index");
    }

}
