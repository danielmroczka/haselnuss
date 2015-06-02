package com.labs.dm.haselnuss.server.http.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by daniel on 2015-06-02.
 */
public class RestHandlerTest {

    private final HttpExchange mockHttpExchange = mock(HttpExchange.class);
    private final Headers headers = mock(Headers.class);

    private RestHandler handler = new RestHandler();

    @Test
    @Ignore
    public void testOnGet() throws Exception {
        when(mockHttpExchange.getRequestMethod()).thenReturn("get");
        when(mockHttpExchange.getResponseHeaders()).thenReturn(headers);
        when(mockHttpExchange.getRequestURI()).thenReturn(mock(URI.class));

        handler.handle(mockHttpExchange);
    }

    @Test
    public void testOnPost() throws Exception {

    }

    @Test
    public void testOnPut() throws Exception {

    }

    @Test
    public void testHandle() throws Exception {

    }
}