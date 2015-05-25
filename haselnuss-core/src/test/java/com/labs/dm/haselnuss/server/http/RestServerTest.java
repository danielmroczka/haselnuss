package com.labs.dm.haselnuss.server.http;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author daniel
 */
public class RestServerTest {
    private static final int port = 9090;

    private static final RestServer server = new RestServer(port);

    public RestServerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        server.start();
    }

    @AfterClass
    public static void tearDownClass() {
        server.stop();
    }

    @Test
    public void testMain() throws Exception {
        assertNotNull(server);
    }

    @Test
    public void main() throws IOException {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port, "GET");
        assertEquals(200, con.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con));
    }

    @Test
    public void admin() throws IOException {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/admin", "GET");
        assertEquals(200, con.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con));
    }

    @Test
    @Ignore
    public void storage() throws IOException {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/storage", "GET");
        assertEquals(200, con.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con));
    }

    @Test
    @Ignore
    public void rest() throws IOException {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/rest", "GET");
        assertEquals(200, con.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con));
    }
}
