package com.labs.dm.haselnuss.core.factory;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by daniel on 2015-05-27.
 */
public class HaselnussInstanceTest {

    private final HaselnussInstance instance = Haselnuss.newInstance();

    @Test
    public void testCreateInMemoryDatabase() throws Exception {
        IStorage storage = instance.createInMemoryDatabase("test");
        assertNotNull(storage);
    }

    @Test
    public void testCreateFileMapDatabase() throws Exception {
        IStorage storage = instance.createFileMapDatabase("test");
        assertNotNull(storage);
    }
}