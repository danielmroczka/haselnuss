package com.labs.dm.haselnuss.server.tcp.command;

import java.io.Serializable;

/**
 * @author daniel
 */
public class Command implements Serializable {

    private final CommandType type;
    private final Serializable key;
    private final Serializable value;

    public Command(CommandType type, Serializable key) {
        this.type = type;
        this.key = key;
        this.value = null;
    }

    public Command(CommandType type, Serializable key, Serializable value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public Serializable getKey() {
        return key;
    }

    public CommandType getType() {
        return type;
    }

    public Serializable getValue() {
        return value;
    }

    public enum CommandType {
        GET, PUT, DELETE
    }

}
