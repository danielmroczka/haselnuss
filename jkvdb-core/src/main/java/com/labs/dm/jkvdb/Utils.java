package com.labs.dm.jkvdb;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author daniel
 */
public class Utils
{

    public static int pid()
    {
        RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
        if (rmxb != null && rmxb.getName() != null)
        {
            String pid = rmxb.getName().substring(0, rmxb.getName().indexOf("@"));
            return Integer.valueOf(pid);
        }
        else
        {
            return 0;
        }
    }

}
