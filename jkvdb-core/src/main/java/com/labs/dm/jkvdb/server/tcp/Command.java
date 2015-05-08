package com.labs.dm.jkvdb.server.tcp;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Command implements Serializable
{

    public enum CommandType
    {
        GET, PUT, DELETE
    }

    private final CommandType type;
    private final int value;

    public int getValue()
    {
        return value;
    }

    public Command(CommandType type, int value)
    {
        this.type = type;
        this.value = value;
    }

}
