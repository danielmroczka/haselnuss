package com.labs.dm.jkvdb;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author daniel
 */
public class Utils {

    public static int pid() {
        try {
            java.lang.management.RuntimeMXBean runtime = java.lang.management.ManagementFactory.getRuntimeMXBean();
            java.lang.reflect.Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
            sun.management.VMManagement mgmt = (sun.management.VMManagement) jvm.get(runtime);
            java.lang.reflect.Method pid_method = mgmt.getClass().getDeclaredMethod("getProcessId");
            pid_method.setAccessible(true);
            RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
            System.out.println(rmxb.getName());
            return (Integer) pid_method.invoke(mgmt);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            
        }
        return -1;

    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println(pid());
    }
}
