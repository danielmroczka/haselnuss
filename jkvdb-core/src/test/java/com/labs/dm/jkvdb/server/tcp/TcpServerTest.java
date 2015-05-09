package com.labs.dm.jkvdb.server.tcp;

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
public class TcpServerTest {
    
    public TcpServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testRunServer() throws Exception {
        TcpServer instance = new TcpServer();
        instance.runServer();
        
        TcpConnection connection = new TcpConnection("localhost", 6543);
        connection.connect();
        assertTrue(connection.isConnected());
        connection.close();
        
        instance.stopServer();
    }
    
}
