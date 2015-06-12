package com.labs.dm.haselnuss.server.tcp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ConnectException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author daniel
 */
public class TcpConnectionTest {

    private final TcpServer server = new TcpServer(6543);

    @Before
    public void setUp() throws Exception {
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.close();
    }

    @Test
    public void shouldConnectOnCorrectPort() throws Exception {
        try (TcpConnection conn = new TcpConnection("localhost", 6543)) {
            conn.connect();
            assertTrue(conn.isConnected());
        }
    }

    @Test(expected = ConnectException.class)
    public void shouldNotConnectOnWrongPort() throws Exception {
        try (TcpConnection conn = new TcpConnection("localhost", 12345)) {
            conn.connect();
            fail("Should not connect on wrong port");
        }
    }

    @Test(expected = ConnectException.class)
    public void shouldNotConnectOnClosedServer() throws Exception {
        server.close();
        try (TcpConnection conn = new TcpConnection("localhost", 6543)) {
            conn.connect();
            fail("Should not connect on closd server");
        }
    }
}


