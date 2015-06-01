package com.labs.dm.haselnuss.example.server.tcp;

import com.labs.dm.haselnuss.server.tcp.Command;
import com.labs.dm.haselnuss.server.tcp.Response;
import com.labs.dm.haselnuss.server.tcp.TcpConnection;

import java.io.IOException;

/**
 * @author daniel
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TcpConnection conn = new TcpConnection("localhost", 6543);
        conn.connect();

        Response r = conn.executeCommand(new Command(Command.CommandType.GET, "key1"));
        System.out.println(r.getValue());
        conn.close();
    }
}
