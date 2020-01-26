package net;

import packets.PacketsHandler;

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
        System.out.println(obj);
        if (obj instanceof String) {
            String received = (String) obj;
            if(received.equals("playersinfo")) {
                sendPacket(serverGame.playersInfo);
                System.out.println("packet senttt");

            } else if (received.contains("[msg]")) {
                opponent.sendPacket(obj);
            } else if (received.equals("joinme")) {
                serverGame.addReadyClient(this);
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            serverGame.readPlayerInfo(map);
        }
    }

    public void addOpponent(ServerHandler opponent) {
        this.opponent = opponent;
    }
}
