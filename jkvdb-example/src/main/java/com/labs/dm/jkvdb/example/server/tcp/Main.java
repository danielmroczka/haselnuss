package com.labs.dm.jkvdb.example.server.tcp;

import com.labs.dm.jkvdb.server.tcp.Command;
import com.labs.dm.jkvdb.server.tcp.Response;
import com.labs.dm.jkvdb.server.tcp.TcpConnection;
import java.io.IOException;

/**
 *
 * @author daniel
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TcpConnection conn = new TcpConnection("localhost", 1234);
        conn.connect();

        Response r = conn.executeCommand(new Command("Test", 123));
        System.out.println(r.getValue());
        r = conn.executeCommand(new Command("Echo", 1234));

        System.out.println(r.getValue());
    }
}
