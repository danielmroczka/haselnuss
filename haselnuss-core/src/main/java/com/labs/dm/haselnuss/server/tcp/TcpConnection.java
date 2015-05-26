package com.labs.dm.haselnuss.server.tcp;

import com.labs.dm.haselnuss.core.IFileStorage;
import com.labs.dm.haselnuss.core.IStorage;
import com.labs.dm.haselnuss.core.hashmap.FastFileMapStorage;

import java.io.*;
import java.net.Socket;

/**
 * @author daniel
 */
public class TcpConnection implements AutoCloseable {

    private final String host;
    private final int port;
    private Socket socket;

    /**
     * Expected format of incoming url parameter:
     * <p>
     * haselnuss:host:port
     *
     * @param url
     */
    public TcpConnection(String url) {
        this("localhost", 1234);
    }

    public TcpConnection(String host, int port) {
        if (host == null || host.isEmpty() || port <= 0) {
            throw new IllegalArgumentException("Incorrect input values");
        }
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
    }

    public boolean isConnected() {
        return (socket != null && socket.isConnected());
    }

    @Override
    public void close() throws IOException {
        if (isConnected()) {
            socket.close();
        }
    }

    public Response executeCommand(Command command) throws IOException, ClassNotFoundException {
        checkConnection();

        ObjectOutput ois = new ObjectOutputStream(socket.getOutputStream());
        ois.writeObject(command);

        ObjectInput oi = new ObjectInputStream(socket.getInputStream());
        Response response = (Response) oi.readObject();

        return response;
    }

    public IFileStorage getFileStorage(String name) throws IOException {
        IFileStorage storage = new FastFileMapStorage(name);
        storage.load();
        return storage;
    }

    public IStorage getInMemoryStorage(String name) {
        IFileStorage storage = new FastFileMapStorage(name);
        return storage;
    }

    private void checkConnection() {
        if (!isConnected()) {
            throw new RuntimeException("Not connected to " + host);
        }
    }
}
