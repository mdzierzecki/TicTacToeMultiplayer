package net;

import game.Game;
import packets.ClientPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.net.Socket;

public class ClientGame extends Game {

    private Socket socket;
    Connection connection;

    public ClientGame() {
        super(Game.PLAYER_TWO);
        try {
            socket = new Socket("localhost", Game.PORT);
            connection = new Connection(this, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        System.out.println(x + " | " + y);

        if(isMyTurn()) {
            connection.sendPacket(new ClientPacket(x, y));
        }

    }

    @Override
    public void packReceived(Object obj) {
        if (obj instanceof UpdatePacket) {
            UpdatePacket packet = (UpdatePacket) obj;

            fields = packet.getFields();
            currentPlayer = packet.getCurrentPlayer();
        }

        gameWindow.repaint();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
