package com.labs.dm.jkvdb.server.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author daniel
 * @since 28.04.2015
 */
public class TcpServer {

    public static void main(String argv[]) throws Exception {
        new TcpServer().runServer();
    }

    public void runServer() throws IOException, ClassNotFoundException {
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            onAccept(connectionSocket);

        }
    }

    private void onAccept(Socket connectionSocket) throws IOException, ClassNotFoundException {
        System.out.println("onAccept " + connectionSocket.toString());

        InputStream is = connectionSocket.getInputStream();
        ObjectInput ois = new ObjectInputStream(is);
        Command command = (Command) ois.readObject();

        Response r = new Response(new Date().toString());

        OutputStream os = connectionSocket.getOutputStream();
        ObjectOutput out = new ObjectOutputStream(os);
        out.writeObject(r);

    }

}
