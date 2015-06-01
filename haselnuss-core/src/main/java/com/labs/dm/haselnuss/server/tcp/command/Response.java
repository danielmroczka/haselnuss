package com.labs.dm.haselnuss.server.tcp.command;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Response implements Serializable {

    private final Serializable value;
    private final int status;

    private Response() {
        status = 0;
        value = null;
    }

    public Response(Serializable value) {
        status = 0;
        this.value = value;
    }

    public static Response ok() {
        return new Response();
    }

    public Serializable getValue() {
        return value;
    }


    public int getStatus() {
        return status;
    }
}
