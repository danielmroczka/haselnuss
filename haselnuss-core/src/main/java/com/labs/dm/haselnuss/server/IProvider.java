package com.labs.dm.haselnuss.server;

import static com.labs.dm.haselnuss.Consts.*;

import java.io.IOException;

/**
 * Created by daniel on 2015-06-12.
 */
public interface IProvider {

    void start() throws IOException;

    void close() throws IOException;

    SERVER_STATUS status();
}
