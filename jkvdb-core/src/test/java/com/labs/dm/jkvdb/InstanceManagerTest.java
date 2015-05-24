package com.labs.dm.jkvdb;

import com.labs.dm.jkvdb.core.IStorage;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class InstanceManagerTest
{

    @Test
    public void testCreateInMemoryDatabase() throws Exception
    {
        IStorage storage = InstanceManager.createInMemoryDatabase("test");
        assertNotNull(storage);
    }

    @Test
    public void testCreateFileMapDatabase() throws Exception
    {
        IStorage storage = InstanceManager.createFileMapDatabase("test");
        assertNotNull(storage);
    }
}