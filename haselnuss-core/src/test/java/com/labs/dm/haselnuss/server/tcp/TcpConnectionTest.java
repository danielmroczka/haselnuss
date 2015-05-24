package com.labs.dm.haselnuss.server.tcp;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author daniel
 */
public class TcpConnectionTest {

    private static TcpServer server;

    @BeforeClass
    public static void setUpClass() throws IOException, ClassNotFoundException {
        server = new TcpServer();
        server.runServer();
    }

    @AfterClass
    public static void tearDownClass() {
        server.stopServer();
    }

    @Test
    public void test() throws IOException {
        try (TcpConnection conn = new TcpConnection("localhost", 6543)) {
            conn.connect();
            assertTrue(conn.isConnected());
        }
    }

    @Test(expected = ConnectException.class)
    public void testDifferentPort() throws IOException {
        try (TcpConnection conn = new TcpConnection("localhost", 12345)) {
            conn.connect();
            fail("Should fail");
        }
    }

}


