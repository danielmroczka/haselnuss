package com.labs.dm.haselnuss;

import com.labs.dm.haselnuss.core.IStorage;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class InstanceManagerTest {

    @Test
    public void testCreateInMemoryDatabase() throws Exception {
        IStorage storage = InstanceManager.createInMemoryDatabase("test");
        assertNotNull(storage);
    }

    @Test
    public void testCreateFileMapDatabase() throws Exception {
        IStorage storage = InstanceManager.createFileMapDatabase("test");
        assertNotNull(storage);
    }
}