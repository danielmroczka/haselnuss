package com.labs.dm.haselnuss.example.server.tcp;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;
import com.labs.dm.haselnuss.server.tcp.Command;
import com.labs.dm.haselnuss.server.tcp.Response;
import com.labs.dm.haselnuss.server.tcp.TcpConnection;
import com.labs.dm.haselnuss.server.tcp.TcpServer;

import java.io.IOException;
import java.util.Date;

/**
 * @author daniel
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TcpServer tcp = new TcpServer();
        tcp.runServer();

        IStorage storage = Haselnuss.createHaselnussInstance().createSharedInMemoryDatabase("tcp");
        storage.put("key1", new Date());
        TcpConnection conn = new TcpConnection("localhost", 6543);
        conn.connect();

        Response r = conn.executeCommand(new Command(Command.CommandType.GET, "key1"));
        System.out.println(r.getValue());
        conn.close();

        tcp.stopServer();
    }
}
