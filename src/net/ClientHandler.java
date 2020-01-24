package net;

import java.net.Socket;

public class ClientHandler extends PacketsHandler {

    public ClientHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void packReceived(Object obj) {
        if (obj instanceof String) {
            System.out.println(obj);
        }
    }
}
