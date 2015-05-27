package com.labs.dm.haselnuss.example;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IFileStorage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by daniel on 2015-05-27.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        IFileStorage storage = Haselnuss.createHaselnussInstance().createFileMapDatabase("dupa");
        storage.load();
        storage.put(100, "value1");
        storage.put(101, "value2");
        Collection<Serializable> col = storage.getAll();
        for (Serializable s : col) {
            System.out.println(s);
        }
        storage.flush();
        storage.close();
    }
}
