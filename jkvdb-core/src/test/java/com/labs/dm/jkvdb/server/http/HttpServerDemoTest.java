package com.labs.dm.jkvdb.server.http;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author daniel
 */
public class HttpServerDemoTest
{

    private static final HttpServerDemo server = new HttpServerDemo();

    public HttpServerDemoTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws IOException
    {
        server.start();
    }

    @AfterClass
    public static void tearDownClass()
    {
        server.stop();
    }

    @Test
    public void testMain() throws Exception
    {
        assertNotNull(server);
    }

    @Test
    public void startup() throws IOException
    {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:8080", "GET");
        assertEquals(200, con.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con));

    }

}
