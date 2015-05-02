package com.labs.dm.jkvdb;

import com.labs.dm.jkvdb.server.http.HttpServerDemo;
import com.labs.dm.jkvdb.server.tcp.TcpServer;
import java.io.IOException;

/**
 *
 * @author daniel
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Welcome to jkvdb");

        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "--rest": {
                    HttpServerDemo server = new HttpServerDemo();
                    server.start();
                    break;
                }
                case "--tcp": {
                    TcpServer server = new TcpServer();
                    server.runServer();
                    break;
                }
            }
        }

        System.out.println("Usage:");
        System.out.println("--rest: starts rest server");
        System.out.println("--tcp: starts tcp server");
    }
}
