package com.labs.dm.haselnuss.example.server.tcp;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.server.tcp.TcpConnection;

import java.io.IOException;

/**
 * @author daniel
 */
public class Main
{

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        TcpConnection conn = new TcpConnection("localhost", 6543);
        conn.connect();

        IFileStorage storage = conn.getFileStorage("settings");
        storage.put("host", "127.0.0.1");
        storage.put("port", "8080");
        storage.flush();
        System.out.println(storage.get("port"));
    }
}
