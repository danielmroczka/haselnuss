package com.labs.dm.haselnuss.utils;

import com.labs.dm.haselnuss.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author daniel
 */
public class UtilsTest {

    @Test
    public void testMain() throws Exception {
        int pid = Utils.pid();
        assertTrue(pid >= 1024);
        assertTrue(pid <= 65536);
    }

}
