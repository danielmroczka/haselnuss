package com.labs.dm.haselnuss.server.tcp;

import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author daniel
 */
public class TcpConnectionTest {

    @Test
    public void test() throws IOException, ClassNotFoundException, InterruptedException {
        TcpServer server = new TcpServer(6543);
        server.runServer();
        try (TcpConnection conn = new TcpConnection("localhost", 6543)) {
            conn.connect();
            assertTrue(conn.isConnected());
        }
        server.stopServer();
    }

    @Test(expected = ConnectException.class)
    public void testDifferentPort() throws IOException, ClassNotFoundException, InterruptedException {
        TcpServer server = new TcpServer(6544);
        server.runServer();
        try (TcpConnection conn = new TcpConnection("localhost", 12345)) {
            conn.connect();
            fail("Should fail");
        }
        server.stopServer();
    }

    @Test(expected = ConnectException.class)
    public void test2() throws IOException, ClassNotFoundException {
        TcpServer server = new TcpServer(8787);
        server.runServer();
        server.stopServer();
        try (TcpConnection conn = new TcpConnection("localhost", 8787)) {
            conn.connect();
        }
    }
}


