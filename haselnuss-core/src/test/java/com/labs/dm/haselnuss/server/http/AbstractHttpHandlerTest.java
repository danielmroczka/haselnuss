package com.labs.dm.haselnuss.server.http;

import com.labs.dm.haselnuss.server.http.handlers.AbstractHttpHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author daniel
 */
public class AbstractHttpHandlerTest {

    private final HttpExchange mockHttpExchange = mock(HttpExchange.class);

    @Test
    public void testRenderPage() throws IOException {
        AbstractHttpHandler instance = new AbstractHttpHandlerImpl();
        when(mockHttpExchange.getRequestMethod()).thenReturn("get");
        instance.handle(mockHttpExchange);
    }

    @Test
    public void testRenderPage2() throws IOException {
        AbstractHttpHandler instance = new AbstractHttpHandlerImpl();
        when(mockHttpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(mockHttpExchange.getResponseBody()).thenReturn(new OutputStream() {

            @Override
            public void write(int b) throws IOException {

            }
        });
        instance.renderPage(mockHttpExchange, "admin");

    }

    public class AbstractHttpHandlerImpl extends AbstractHttpHandler {

        @Override
        public void onGet(HttpExchange he) throws IOException {

        }

    }

}
