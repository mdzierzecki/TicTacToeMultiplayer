package net;

import game.ClientGame;
import packets.ClientPacket;
import packets.PacketsHandler;
import packets.UpdatePacket;

import java.net.Socket;

public class ClientHandler extends PacketsHandler {

    ClientGame clientGame;

    public ClientHandler(Socket socket, ClientGame clientGame) {
        super(socket);
        this.clientGame = clientGame;
    }

    @Override
    public void packReceived(Object obj) {
        if (obj instanceof String) {
            System.out.println(obj);

        } else if (obj instanceof PlayersInfo) {
            System.out.println(obj);
            PlayersInfo playersInfo = (PlayersInfo) obj;
            clientGame.addPlayer(playersInfo.playersInfo);
        } else if (obj instanceof Integer) {
            System.out.println("Integer: " + obj);
            int player = (Integer) obj;
            clientGame.setPlayer(player);
        } else if (obj instanceof UpdatePacket) {
            System.out.println(obj);
            UpdatePacket packet = (UpdatePacket) obj;
            clientGame.set(packet);
        }
    }

}
