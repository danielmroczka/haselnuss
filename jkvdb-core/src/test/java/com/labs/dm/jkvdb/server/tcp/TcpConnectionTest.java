package com.labs.dm.jkvdb.server.tcp;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.IStorage;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class TcpConnectionTest {

    private static TcpServer server;

    public TcpConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException, ClassNotFoundException {
        server = new TcpServer();
        server.runServer();
    }

    @AfterClass
    public static void tearDownClass() {
        server.stopServer();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() throws IOException {
        try (TcpConnection conn = new TcpConnection("localhost", 6543)) {
            conn.connect();
            assertTrue(conn.isConnected());
        }
    }
}
