package com.labs.dm.jkvdb.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author daniel
 */
public class HttpClientUtil
{

    public static HttpURLConnection httpURLConnection(String url, String method) throws IOException
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        return con;
    }

    public static String responseBody(HttpURLConnection con) throws IOException
    {
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())))
        {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
        }

        return response.toString();
    }
}
