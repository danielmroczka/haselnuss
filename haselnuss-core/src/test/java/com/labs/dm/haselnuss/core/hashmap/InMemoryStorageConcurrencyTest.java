package com.labs.dm.haselnuss.core.hashmap;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;

/**
 * Created by daniel on 2015-05-28.
 */
public class InMemoryStorageConcurrencyTest {
    public static void main(String[] args) {
        IStorage storage1 = Haselnuss.createHaselnussInstance().createInMemoryDatabase("test");
        IStorage storage2 = Haselnuss.createHaselnussInstance().createInMemoryDatabase("test");

        storage1.put(123, "abc");
        System.out.println(storage2.get(123));
    }
}
