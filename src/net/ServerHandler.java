package net;

import packets.PacketsHandler;
import packets.UpdatePacket;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

public class ServerHandler extends PacketsHandler {

    ServerGame serverGame;
    ServerHandler opponent;
    String playerId;

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
                if (this.opponent == null) {
                    this.sendPacket("waitingForOpponent");
                }
            } else if (received.equals("logout")) {
                serverGame.logoutPlayer(playerId);
                try {
                    this.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            int randomInt = new Random().nextInt(85) + 1000;
            String playerId = map.get("name") + "-" + randomInt;
            this.playerId = playerId;
            map.replace("name", playerId);
            serverGame.readPlayerInfo(map);
        } else if(obj instanceof UpdatePacket) {
            opponent.sendPacket(obj);
        }
    }

    public void addOpponent(ServerHandler opponent) {
        this.opponent = opponent;
    }
}
