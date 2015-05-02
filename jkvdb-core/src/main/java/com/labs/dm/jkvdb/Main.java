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

        for (int id = 0; id < args.length; id++) {
            String arg = args[id];
            switch (arg.toLowerCase()) {
                case "-server": {
                    if (id < args.length) {
                        String type = args[id + 1];

                        if (type != null) {
                            switch (type.trim().toLowerCase()) {
                                case "tcp": {
                                    TcpServer server = new TcpServer();
                                    server.runServer();
                                    break;
                                }
                                case "http": {
                                    HttpServerDemo server = new HttpServerDemo();
                                    server.start();
                                    break;
                                }
                                default: {
                                    System.err.println("Unrecognized server type!");
                                }
                            }

                        }
                    } else {
                        System.err.println("Missing server type argument");
                    }
                            
                }
            }
        }

        System.out.println("Usage:");
        System.out.println("-server http - starts rest server");
        System.out.println("-server tcp - starts tcp server");
    }
}
