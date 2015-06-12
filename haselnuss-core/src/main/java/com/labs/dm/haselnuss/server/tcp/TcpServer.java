package com.labs.dm.haselnuss.server.tcp;

import com.labs.dm.haselnuss.Consts;
import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.server.IProvider;
import com.labs.dm.haselnuss.server.tcp.command.Command;
import com.labs.dm.haselnuss.server.tcp.command.Response;
import com.labs.dm.haselnuss.server.tcp.command.TcpCommandProcess;
import com.labs.dm.haselnuss.utils.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.labs.dm.haselnuss.Consts.CONFIG_FILENAME;

/**
 * @author daniel
 * @since 28.04.2015
 */
public class TcpServer implements IProvider, AutoCloseable {

    private static final Logger logger = Logger.getLogger(TcpServer.class.getSimpleName());
    private final Properties properties;
    private final TcpCommandProcess tcpCommandProcess = new TcpCommandProcess();
    private ServerSocket serverSocket;
    private volatile boolean active;
    private int instances;

    public TcpServer() {
        this.properties = new Properties();
        loadConfiguration();
    }

    public TcpServer(Properties properties) {
        this.properties = properties;
    }

    public TcpServer(int port) {
        this.properties = new Properties();
        properties.setProperty("tcp.port", String.valueOf(port));
    }

    public static void main(String argv[]) throws Exception {

        TcpServer server = Haselnuss.newInstance().createTcpServer();
        server.start();
    }

    public void start() throws IOException {
        logger.info("Starting server...");
        serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("tcp.port", Consts.TCP_DEFAULT_PORT)));
        logger.log(Level.INFO, "Server is listening on port: " + serverSocket.getLocalPort());
        logger.log(Level.INFO, "PID: " + Utils.pid());
        active = true;

        new Thread(new ServerThread()).start();
    }

    private void createReadThread(final Socket socket) {
        logger.log(Level.INFO, "onAccept {0} clients: {1}", new Object[]{socket.toString(), ++instances});

        new Thread() {

            @Override
            public void run() {
                while (active) {
                    try {
                        InputStream is = socket.getInputStream();
                        if (is.available() > 0) {
                            ObjectInput ois = new ObjectInputStream(is);
                            Command command = (Command) ois.readObject();
                            Response response = tcpCommandProcess.commandProcess(command);
                            ObjectOutput out = new ObjectOutputStream(socket.getOutputStream());
                            out.writeObject(response);
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        logger.log(Level.SEVERE, "", e);
                    }
                }
            }
        }.start();
    }

    @Override
    public void close() throws IOException {
        logger.info("Stopping server...");
        active = false;
        if (serverSocket != null) {
            serverSocket.close();
        }
        logger.info("Server stopped");
    }

    private void loadConfiguration() {
        try (InputStream input = TcpServer.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME)) {
            properties.load(input);
        } catch (FileNotFoundException fnfe) {
            logger.severe("Configuration file not found.");
        } catch (IOException ex) {
            logger.severe(ex.getLocalizedMessage());
        }
    }

    private class ServerThread implements Runnable {

        @Override
        public void run() {

            while (active) {

                try {
                    Socket socket = serverSocket.accept();
                    createReadThread(socket);
                } catch (SocketException se) {
                    logger.finest(se.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
