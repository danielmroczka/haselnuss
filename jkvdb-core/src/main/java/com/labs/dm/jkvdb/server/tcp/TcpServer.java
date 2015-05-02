package com.labs.dm.jkvdb.server.tcp;

import com.labs.dm.jkvdb.Consts;
import static com.labs.dm.jkvdb.Consts.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Properties;

/**
 * @author daniel
 * @since 28.04.2015
 */
public class TcpServer {

    private final Properties properties;

    public TcpServer() {
        this.properties = new Properties();
        loadConfiguration();
    }

    public TcpServer(Properties properties) {
        this.properties = properties;
    }

    public static void main(String argv[]) throws Exception {
        System.out.println("Starting server...");
        TcpServer server = new TcpServer();
        server.runServer();
    }

    public void runServer() throws IOException, ClassNotFoundException {
        ServerSocket welcomeSocket = new ServerSocket(Integer.valueOf(properties.getProperty("tcp.port", Consts.TCP_DEFAULT_PORT)));
        System.out.println("Server is listening on port: " + welcomeSocket.getLocalPort());

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            onAccept(connectionSocket);

        }
    }

    private void onAccept(Socket connectionSocket) throws IOException, ClassNotFoundException {
        try {
            System.out.println("onAccept " + connectionSocket.toString());

            InputStream is = connectionSocket.getInputStream();
            ObjectInput ois = new ObjectInputStream(is);
            Command command = (Command) ois.readObject();
            Response response = commandProccess(command);
            OutputStream os = connectionSocket.getOutputStream();
            ObjectOutput out = new ObjectOutputStream(os);
            out.writeObject(response);
        } catch (SocketException se) {
            System.err.println(se);
        }
    }

    private Response commandProccess(Command command) {
        return new Response(new Date().toString());
    }

    private void loadConfiguration() {
        try (InputStream input = TcpServer.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME)) {
            properties.load(input);
        } catch (FileNotFoundException fnfe) {
            System.err.println("Configuration file not found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
