package com.labs.dm.haselnuss.server.tcp;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Command implements Serializable {

    private final CommandType type;
    private final Serializable key;

    public Command(CommandType type, Serializable key) {
        this.type = type;
        this.key = key;
    }

    public Serializable getKey() {
        return key;
    }

    public enum CommandType {
        GET, PUT, DELETE
    }

}
