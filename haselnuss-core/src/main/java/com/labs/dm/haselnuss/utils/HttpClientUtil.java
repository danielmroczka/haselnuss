package com.labs.dm.haselnuss.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author daniel
 */
public class HttpClientUtil {

    /**
     *
     *
     * @param url
     * @param method
     * @return
     * @throws IOException
     */
    public static HttpURLConnection httpURLConnection(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        return con;
    }

    /**
     *
     *
     * @param conn
     * @return
     * @throws IOException
     */
    public static String responseBody(HttpURLConnection conn) throws IOException {
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }
}
