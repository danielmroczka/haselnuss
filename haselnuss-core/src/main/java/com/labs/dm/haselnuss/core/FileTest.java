package com.labs.dm.haselnuss.core;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by daniel on 2015-05-27.
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("test.file", "rw");
        //  raf.writeChars("ala");
        //
        // raf.write("If you need to insert plain text next to a form label within a horizontal form, use the".getBytes());
        ByteBuffer buffer = ByteBuffer.allocate(10);
        FileChannel fc = raf.getChannel();
        fc.position(0);  // position to the byte you want to start reading
        fc.read(buffer);  // read data into buffer
        byte[] data = buffer.array();

        byte[] newbytes = "PLAIN TEXT".getBytes();
        ByteBuffer buf = ByteBuffer.allocate(20);
        buf.put(newbytes);
        buf.flip();
        fc.write(buf, 20);

        String str = new String(data, "UTF-8");
        System.out.println(str);
    }
}
