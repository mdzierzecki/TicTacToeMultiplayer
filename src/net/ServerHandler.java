package net;

import packets.PacketsHandler;

import java.net.Socket;
import java.util.Map;

public class ServerHandler extends PacketsHandler {

    ServerGame serverGame;

    public ServerHandler(Socket socket, ServerGame serverGame) {
        super(socket);

        this.serverGame = serverGame;
    }

    @Override
    public void packReceived(Object obj) {
        if (obj instanceof String) {
            String received = (String) obj;
            System.out.println(received);
            if(received.equals("playersinfo")) {
                sendPacket(serverGame.playersInfo);
                System.out.println("packet senttt");
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            serverGame.readPlayerInfo(map);
        }
    }
}
