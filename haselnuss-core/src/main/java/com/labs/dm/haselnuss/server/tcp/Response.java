package com.labs.dm.haselnuss.server.tcp;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Response implements Serializable
{

    private final String value;

    public Response(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

}
