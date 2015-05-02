package com.labs.dm.jkvdb.server.tcp;

import java.io.*;
import java.net.Socket;

/**
 * @author daniel
 * @since 28.04.2015
 */
public class TcpClient {

    public static void main(String argv[]) throws Exception {
        Command command = new Command("hello", 123);
        
        try (Socket clientSocket = new Socket("localhost", 6789)) {
            OutputStream os = clientSocket.getOutputStream();
            ObjectOutput ois = new ObjectOutputStream(os);
            ois.writeObject(command);

            InputStream is = clientSocket.getInputStream();
            ObjectInput oi = new ObjectInputStream(is);
            Response response = (Response) oi.readObject();

            System.out.println("Read from Server: " + response.getValue());
        }
    }

}
