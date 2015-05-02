package com.labs.dm.jkvdb.server.tcp;

import com.labs.dm.jkvdb.core.IFileStorage;
import com.labs.dm.jkvdb.core.IStorage;
import com.labs.dm.jkvdb.core.hashmap.SimpleFileMapStorage;
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
        checkConnection();
        
        ObjectOutput ois = new ObjectOutputStream(socket.getOutputStream());
        ois.writeObject(command);

        ObjectInput oi = new ObjectInputStream(socket.getInputStream());
        Response response = (Response) oi.readObject();
        
        return response;
    }
    
    public IFileStorage getFileStorage(String name) {
        IFileStorage storage = new SimpleFileMapStorage(name);
        return storage;
    }
    
    public IStorage getInMemoryStorage(String name) {
        IFileStorage storage = new SimpleFileMapStorage(name);
        return storage;
    }
    
    private void checkConnection() {
        if (socket == null || !socket.isConnected()) {
            throw new RuntimeException("Not connected to " + host);
        }        
    }
}
