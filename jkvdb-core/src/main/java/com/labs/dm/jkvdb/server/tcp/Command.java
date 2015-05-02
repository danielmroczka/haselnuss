package com.labs.dm.jkvdb.server.tcp;

import java.io.Serializable;

/**
 *
 * @author daniel 
 */
public class Command implements Serializable {
    
    private final String text;
    private final int value;
    
    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    public Command(String text, int value) {
        this.text = text;
        this.value = value;
    }
    
}
