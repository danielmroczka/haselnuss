package com.labs.dm.haselnuss.server.http;

import com.labs.dm.haselnuss.Consts;
import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.utils.HttpClientUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author daniel
 */
public class RestServerTest {
    private static final int port = 9090;

    private static final RestServer server = Haselnuss.newInstance().createRestServer(9090);

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
        System.out.println("RestServerTest::Starting test: " + description.getMethodName());
        }
    };

    @BeforeClass
    public static void setUpClass() throws IOException {
        IFileStorage storage = Haselnuss.newInstance().createFileMapDatabase("test");
        storage.load();
        storage.put("key", "restservertest");
        storage.flush();
        storage.close();
        server.start();
    }

    @AfterClass
    public static void tearDownClass() {
        server.close();
    }

    @Test
    public void testMain() throws Exception {
        assertNotNull(server);
    }

    @Test
    public void main() throws IOException {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port, "GET");
        assertEquals(200, con.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con));
    }

    @Test
    public void admin() throws IOException {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/admin", "GET");
        assertEquals(200, con.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con));
    }

    @Test
    public void storage() throws IOException {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/storage", "GET");
        //assertEquals(200, con.getResponseCode());

        //assertNotNull(HttpClientUtil.responseBody(con));
    }

    @Test
    public void shouldPost() throws Exception {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/rest/test/key", "POST");
        HttpClientUtil.responseBody(con);
        assertEquals(200, con.getResponseCode());
        assertNotNull(con);
    }

    @Test
    public void shouldGet() throws Exception {
        HttpURLConnection con = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/rest/test/key", "GET");
        HttpClientUtil.responseBody(con);
        assertEquals(200, con.getResponseCode());
        assertNotNull(con);
    }

    @Test
    public void rest() throws IOException {
        HttpURLConnection con1 = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/rest/test/key", "POST");
        HttpURLConnection con2 = HttpClientUtil.httpURLConnection("http://localhost:" + port + "/rest/test/key", "GET");
        assertEquals(200, con1.getResponseCode());
        assertEquals(200, con2.getResponseCode());

        assertNotNull(HttpClientUtil.responseBody(con2));
    }


    @Test
    public void shouldReturnsCorrectStatus() throws Exception {
        RestServer instance = new RestServer(9876);
        assertEquals(Consts.SERVER_STATUS.STOPPED, instance.status());
        /*instance.start();
        assertEquals(Consts.SERVER_STATUS.STARTED, instance.status());
        instance.close();
        assertEquals(Consts.SERVER_STATUS.STOPPED, instance.status());*/
    }
}
