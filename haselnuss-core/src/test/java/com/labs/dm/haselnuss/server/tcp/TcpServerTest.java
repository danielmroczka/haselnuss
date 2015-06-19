package com.labs.dm.haselnuss.server.tcp;

import com.labs.dm.haselnuss.Consts;
import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.server.tcp.command.Command;
import com.labs.dm.haselnuss.server.tcp.command.Response;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.net.BindException;

import static org.junit.Assert.*;

/**
 * @author daniel
 */
public class TcpServerTest {

    private final int TCP_PORT = Integer.parseInt(Haselnuss.newInstance().getProperties().getProperty("tcp.port"));

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            System.out.println("TcpServerTest::Starting test: " + description.getMethodName());
        }
    };

    private TcpServer instance;

    @After
    public void tearDown() throws Exception {
        if (instance != null) {
            instance.close();
        }
    }

    @Test
    public void testRunServer() throws Exception {
        instance = new TcpServer(TCP_PORT);
        instance.start();

        try (TcpConnection connection = new TcpConnection("localhost", TCP_PORT)) {
            connection.connect();
            assertTrue(connection.isConnected());
        }
    }

    @Test
    public void testLoadFromProperties() throws Exception {
        instance = new TcpServer(TCP_PORT);
        instance.start();

        try (TcpConnection connection = new TcpConnection("localhost", TCP_PORT)) {
            connection.connect();
            assertTrue(connection.isConnected());
        }
    }

    @Test
    public void testDefaultPort() throws Exception {
        instance = new TcpServer(TCP_PORT);
        instance.start();

        try (TcpConnection connection = new TcpConnection("localhost", Consts.TCP_DEFAULT_PORT)) {
            connection.connect();
            assertTrue(connection.isConnected());
        }

    }

    @Test
    public void simpleCommand() throws Exception {
        instance = new TcpServer(TCP_PORT);
        instance.start();

        try (TcpConnection connection = new TcpConnection("localhost", TCP_PORT)) {
            connection.connect();
            connection.executeCommand(new Command(Command.CommandType.GET, "key123"));
        }
    }

    @Test
    public void testCommand() throws Exception {
        instance = new TcpServer(TCP_PORT);
        instance.start();

        try (TcpConnection connection = new TcpConnection("localhost", TCP_PORT)) {
            connection.connect();

            Response response = connection.executeCommand(new Command(Command.CommandType.PUT, "key123", "val123"));
            assertEquals(0, response.getStatus());

            response = connection.executeCommand(new Command(Command.CommandType.GET, "key123"));
            assertEquals("val123", response.getValue());

            response = connection.executeCommand(new Command(Command.CommandType.DELETE, "key123"));
            assertEquals(0, response.getStatus());

            response = connection.executeCommand(new Command(Command.CommandType.GET, "key123"));
            assertNull(response.getValue());

            assertTrue(connection.isConnected());
        }
    }

    @Test(expected = BindException.class)
    public void shouldRunOnlyOneInstance() throws Exception {
        try (TcpServer instance1 = new TcpServer(TCP_PORT); TcpServer instance2 = new TcpServer(TCP_PORT)) {
            instance1.start();
            instance2.start();
        }
    }

    @Test
    public void loadTest() throws Exception {
        instance = new TcpServer(TCP_PORT);
        instance.start();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Worker());
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }

    @Test
    public void shouldReturnsCorrectStatus() throws Exception {
        instance = new TcpServer(9876);
        assertEquals(Consts.SERVER_STATUS.STOPPED, instance.status());
        instance.start();
        assertEquals(Consts.SERVER_STATUS.STARTED, instance.status());
        instance.close();
        assertEquals(Consts.SERVER_STATUS.STOPPED, instance.status());
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {

                try (TcpConnection connection = new TcpConnection("localhost", TCP_PORT)) {
                    connection.connect();
                    connection.executeCommand(new Command(Command.CommandType.PUT, "key123", "val123"));
                    Response r = connection.executeCommand(new Command(Command.CommandType.GET, "key123"));
                    System.out.println(r.getValue());
                    assertEquals(0, r.getStatus());
                    assertEquals("val123", r.getValue());
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
