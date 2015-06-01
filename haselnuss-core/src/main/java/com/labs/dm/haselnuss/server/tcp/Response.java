package com.labs.dm.haselnuss.server.tcp;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Response implements Serializable {

    private final Serializable value;

    public Response(Serializable value) {
        this.value = value;
    }

    public Serializable getValue() {
        return value;
    }


}
