package net;

import game.ClientGame;
import packets.ClientPacket;
import packets.GameEndPacket;
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
            String status = (String) obj;
            clientGame.statusUpdate(status);
        } else if (obj instanceof PlayersInfo) {
            PlayersInfo playersInfo = (PlayersInfo) obj;
            clientGame.addPlayer(playersInfo.playersInfo);
        } else if (obj instanceof Integer) {
            int player = (Integer) obj;
            clientGame.setPlayer(player);
            clientGame.setTurn();
        } else if (obj instanceof UpdatePacket) {
            UpdatePacket packet = (UpdatePacket) obj;
            clientGame.set(packet);
            clientGame.checkResult();
        } else if(obj instanceof GameEndPacket) {
            GameEndPacket packet = (GameEndPacket) obj;
            clientGame.showWinner(packet.getWinner());
        }
    }

}
