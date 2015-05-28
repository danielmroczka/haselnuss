package com.labs.dm.haselnuss.utils;

import org.junit.Test;

import java.net.HttpURLConnection;

import static org.junit.Assert.*;

public class HttpClientUtilTest
{

    @Test
    public void testHttpURLConnection() throws Exception
    {
        assertNotNull(HttpClientUtil.httpURLConnection("http://127.0.0.1", "GET"));
    }

    @Test
    public void testResponseBody() throws Exception
    {
        HttpURLConnection conn = HttpClientUtil.httpURLConnection("http://www.google.com", "GET");
        assertNotNull(HttpClientUtil.responseBody(conn));
    }
}