package com.labs.dm.jkvdb.server.tcp;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author daniel
 */
public class TcpConnection {

    private final String host;
    private final int port;
    private Socket socket;

    public TcpConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
    }

    public void close() throws IOException {
        if (socket != null && socket.isConnected()) {
            socket.close();
        }
    }

    public Response executeCommand(Command command) throws IOException, ClassNotFoundException {
        if (socket == null || !socket.isConnected()) {
            throw new RuntimeException("Not connected to " + host);
        }
        
        ObjectOutput ois = new ObjectOutputStream(socket.getOutputStream());
        ois.writeObject(command);

        ObjectInput oi = new ObjectInputStream(socket.getInputStream());
        Response response = (Response) oi.readObject();
        
        return response;
    }
}
