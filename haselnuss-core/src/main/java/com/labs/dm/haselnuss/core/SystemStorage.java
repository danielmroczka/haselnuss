package com.labs.dm.haselnuss.core;

import com.labs.dm.haselnuss.Haselnuss;

import java.io.IOException;

/**
 * Created by daniel on 2015-05-28.
 */
public class SystemStorage {
    private IFileStorage system;

    public void init() throws IOException {
        system = Haselnuss.createHaselnussInstance().createFileMapDatabase("system");
    }

    public void add(String name) {

    }

}