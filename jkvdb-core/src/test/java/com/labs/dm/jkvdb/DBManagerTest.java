package com.labs.dm.jkvdb;

import com.labs.dm.jkvdb.core.IStorage;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBManagerTest
{

    @Test
    public void testCreateInMemoryDatabase() throws Exception
    {
        IStorage storage = DBManager.getInstance().createInMemoryDatabase("test");
        assertNotNull(storage);
    }

    @Test
    public void testCreateFileMapDatabase() throws Exception
    {
        IStorage storage = DBManager.getInstance().createFileMapDatabase("test");
        assertNotNull(storage);
    }
}