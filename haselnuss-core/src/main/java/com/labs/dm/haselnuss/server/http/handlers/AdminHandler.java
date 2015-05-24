package com.labs.dm.haselnuss.server.http.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by daniel on 2015-05-23.
 */
public class AdminHandler extends AbstractHttpHandler {

    static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public void onGet(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        renderPage(exchange, "admin");
    }

}
