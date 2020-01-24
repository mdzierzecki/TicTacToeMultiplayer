package net;

import java.net.Socket;

public class ServerHandler extends PacketsHandler {

    public ServerHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void packReceived(Object obj) {
        if (obj instanceof String) {
            String received = (String) obj;
            System.out.println(received);
            if(received.equals("playersinfo")) {
                sendPacket("Malo graczy");
            }

        }
    }
}
