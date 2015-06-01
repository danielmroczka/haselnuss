package com.labs.dm.haselnuss.server.tcp.command;

import com.labs.dm.haselnuss.Haselnuss;
import com.labs.dm.haselnuss.core.IStorage;

import java.util.logging.Logger;

public class TcpCommandProcess {

    private static final Logger logger = Logger.getLogger(TcpCommandProcess.class.getSimpleName());

    public Response commandProcess(Command command) {
        logger.info("onCommandProcess: type=" + command.getType() + ", key=" + command.getKey());

        IStorage storage = Haselnuss.createHaselnussInstance().createFileMapDatabase("tcp");
        Response response;

        switch (command.getType()) {
            case GET: {
                response = new Response(storage.get(command.getKey()));
                break;
            }

            case PUT: {
                storage.put(command.getKey(), command.getValue());
                response = Response.ok();
                break;
            }

            case DELETE: {
                storage.remove(command.getKey());
                response = Response.ok();
                break;
            }

            default: {
                throw new IllegalArgumentException();
            }
        }

        return response;
    }
}