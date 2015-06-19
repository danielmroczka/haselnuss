package com.labs.dm.haselnuss.server;

import com.labs.dm.haselnuss.Consts;

import java.io.IOException;

/**
 * Created by daniel on 2015-06-12.
 */
public interface IProvider {

    enum TYPE {
        EMBEDDED,
        HTTP,
        TCP
    }

    void start() throws IOException;

    void close() throws IOException;

    Consts.SERVER_STATUS status();
}
