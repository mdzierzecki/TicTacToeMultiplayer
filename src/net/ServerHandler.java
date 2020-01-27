package net;

import packets.PacketsHandler;
import packets.UpdatePacket;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ServerHandler extends PacketsHandler {

    ServerGame serverGame;
    ServerHandler opponent;

    public ServerHandler(Socket socket, ServerGame serverGame) {
        super(socket);
        this.serverGame = serverGame;
    }

    @Override
    public void packReceived(Object obj) {
        if (obj instanceof String) {
            String received = (String) obj;
            if(received.equals("playersinfo")) {
                sendPacket(serverGame.playersInfo);
            } else if (received.contains("[msg]")) {
                opponent.sendPacket(obj);
            } else if (received.equals("joinme")) {
                serverGame.addReadyClient(this);
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            serverGame.readPlayerInfo(map);
        } else if(obj instanceof UpdatePacket) {
            opponent.sendPacket(obj);
        }
    }

    public void addOpponent(ServerHandler opponent) {
        this.opponent = opponent;
    }
}
