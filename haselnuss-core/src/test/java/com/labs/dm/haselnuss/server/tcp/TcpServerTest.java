package com.labs.dm.haselnuss.server.tcp;

import com.labs.dm.haselnuss.Consts;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author daniel
 */
public class TcpServerTest {

    @Test
    public void testRunServer() throws Exception {
        TcpServer instance = new TcpServer();
        instance.runServer();

        try (TcpConnection connection = new TcpConnection("localhost", 6543)) {
            connection.connect();
            assertTrue(connection.isConnected());
        }
        TimeUnit.MILLISECONDS.sleep(10);
        instance.stopServer();
    }

    @Test
    public void testLoadFromProperties() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("tcp.port", "9876");
        TcpServer instance = new TcpServer(properties);
        instance.runServer();

        try (TcpConnection connection = new TcpConnection("localhost", 9876)) {
            connection.connect();
            assertTrue(connection.isConnected());
        }
        TimeUnit.MILLISECONDS.sleep(10);
        instance.stopServer();
    }

    @Test
    public void testDefaultPort() throws Exception {
        Properties properties = new Properties();

        TcpServer instance = new TcpServer(properties);
        instance.runServer();

        try (TcpConnection connection = new TcpConnection("localhost", Integer.valueOf(Consts.TCP_DEFAULT_PORT))) {
            connection.connect();
            assertTrue(connection.isConnected());
        }
        TimeUnit.MILLISECONDS.sleep(10);
        instance.stopServer();
    }

    @Test
    //@Ignore
    public void simpleCommand() throws Exception {
        TcpServer instance = new TcpServer();
        instance.runServer();

        try (TcpConnection connection = new TcpConnection("localhost", 6543)) {
            connection.connect();
            connection.executeCommand(new Command(Command.CommandType.GET, "key123"));
        }

        instance.stopServer();
    }

    @Test
    //@Ignore
    public void testCommand() throws Exception {
        TcpServer instance = new TcpServer();
        instance.runServer();

        try (TcpConnection connection = new TcpConnection("localhost", 6543)) {
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
        TimeUnit.MILLISECONDS.sleep(10);
        instance.stopServer();
    }
}
