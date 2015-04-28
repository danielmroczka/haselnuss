package com.labs.dm.jkvdb;

import com.labs.dm.jkvdb.server.HttpServerDemo;
import java.io.IOException;

/**
 *
 * @author daniel
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to jkvdb");

        //for (String arg : args) {
            //if (arg.equals("-server")) {
                HttpServerDemo server = new HttpServerDemo();
                server.start();
            //}
        //}

        //System.out.println("Usage:");
        //System.out.println("-server: starts http server");
    }
}
