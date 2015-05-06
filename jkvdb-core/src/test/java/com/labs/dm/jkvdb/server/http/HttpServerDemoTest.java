package com.labs.dm.jkvdb.server.http;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class HttpServerDemoTest {

    private static final HttpServerDemo server = new HttpServerDemo();

    public HttpServerDemoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        server.start();
    }

    @AfterClass
    public static void tearDownClass() {
        server.stop();
    }

    @Test
    public void testMain() throws Exception {
        assertNotNull(server);
    }

}
