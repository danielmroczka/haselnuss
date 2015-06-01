package com.labs.dm.haselnuss.server.tcp;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Response implements Serializable {

    private final Serializable value;
    private final int status;

    public Response(Serializable value) {
        status = 0;
        this.value = value;
    }

    public Serializable getValue() {
        return value;
    }


    public int getStatus() {
        return status;
    }
}
