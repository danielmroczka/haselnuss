package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.server.tcp.TcpServer;

import java.io.IOException;

/**
 *
 * Usage:
 *
 * java -jar target/haselnuss-core.jar -server tcp
 * java -jar target/haselnuss-core.jar -server http
 *
 * @author daniel
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Welcome to Haselnuss");

        for (int id = 0; id < args.length; id++) {
            String arg = args[id];
            switch (arg.toLowerCase()) {
                case "-server": {
                    if (id < args.length) {
                        String type = args[id + 1];

                        if (type != null) {
                            switch (type.trim().toLowerCase()) {
                                case "tcp": {
                                    Haselnuss.newInstance().createTcpServer().runServer();
                                    break;
                                }
                                case "http": {
                                    Haselnuss.newInstance().createRestServer().start();
                                    break;
                                }
                                default: {
                                    System.err.println("Unrecognized server type!");
                                    printUsage();
                                }
                            }
                        }
                    } else {
                        System.err.println("Missing server type argument");
                        printUsage();
                    }
                }
            }
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("-server http - starts rest server");
        System.out.println("-server tcp - starts tcp server");
    }
}
