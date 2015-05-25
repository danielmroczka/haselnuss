package com.labs.dm.haselnuss.server.tcp;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Command implements Serializable {

    private final CommandType type;
    private final int value;

    public Command(CommandType type, int value) {
        this.type = type;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public enum CommandType {
        GET, PUT, DELETE
    }

}
