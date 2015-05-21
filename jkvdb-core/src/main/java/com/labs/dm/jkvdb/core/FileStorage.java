package com.labs.dm.jkvdb.core;

import java.beans.Transient;
import java.io.*;

/**
 * Created by daniel on 2015-05-17.
 */
public class FileStorage {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new FileStorage().add("key", "value");
        new FileStorage().get();//add("key", "value");
    }

    public void add(Serializable key, Serializable val) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("test123"));
        os.writeObject(new Foo(100));
        os.writeObject(new Foo(200));
        os.writeObject(new Foo(300));
        os.flush();
    }

    public void get() throws IOException, ClassNotFoundException {
        ObjectInputStream os = new ObjectInputStream(new FileInputStream("test123"));
        System.out.println(os.readObject());
        System.out.println(os.readFields());

        //System.out.println(os.readObject());
        //System.out.println(os.readObject());
        //System.out.println(os.readObject());

    }


}

class Foo implements Serializable {
    int val;

    public Foo(int i) {
        val = i;
    }

    @Override
    @Transient
    public String toString() {
        return "val:" + val;
    }
}