package com.labs.dm.haselnuss.server.tcp;

import com.labs.dm.haselnuss.Consts;
import com.labs.dm.haselnuss.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.labs.dm.haselnuss.Consts.CONFIG_FILENAME;

/**
 * @author daniel
 * @since 28.04.2015
 */
public class TcpServer {

    private static final Logger logger = Logger.getLogger(TcpServer.class.getSimpleName());
    private final Properties properties;
    private ServerSocket serverSocket;
    volatile private boolean active;
    private int instances;

    public TcpServer() {
        this.properties = new Properties();
        loadConfiguration();
    }

    public TcpServer(Properties properties) {
        this.properties = properties;
    }

    public static void main(String argv[]) throws Exception {
        logger.info("Starting server...");
        TcpServer server = new TcpServer();
        server.runServer();
    }

    public void runServer() throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("tcp.port", Consts.TCP_DEFAULT_PORT)));
        logger.log(Level.INFO, "Server is listening on port: {0}", serverSocket.getLocalPort());
        logger.log(Level.INFO, "PID: {0}", Utils.pid());
        active = true;
        new Thread(new Runner()).start();
    }

    public void stopServer() {
        active = false;
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void onAccept(Socket connectionSocket) throws IOException, ClassNotFoundException {
        try {
            logger.log(Level.INFO, "onAccept {0} clients: {1}", new Object[]{connectionSocket.toString(), ++instances});

            InputStream is = connectionSocket.getInputStream();
            ObjectInput ois = new ObjectInputStream(is);
            Command command = (Command) ois.readObject();
            Response response = commandProccess(command);
            OutputStream os = connectionSocket.getOutputStream();
            ObjectOutput out = new ObjectOutputStream(os);
            out.writeObject(response);
        } catch (SocketException se) {
            logger.severe(se.getLocalizedMessage());
        }
    }

    private Response commandProccess(Command command) {
        return new Response(new Date().toString());
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

    class Runner implements Runnable {

        @Override
        public void run() {
            try {
                while (active) {
                    try {
                        Socket connectionSocket = serverSocket.accept();
                        onAccept(connectionSocket);
                    } catch (java.net.SocketException se) {
                        Logger.getLogger(TcpServer.class.getSimpleName()).severe(se.getMessage());
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
