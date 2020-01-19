package net;

import game.Game;
import packets.ClientPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.net.Socket;

public class ClientGame extends Game {

    private Socket socket;
    public Connection connection;

    public ClientGame() {
        super(Game.PLAYER_TWO);

    }


    @Override
    public void connect(int port, String host) {
        try {
            socket = new Socket(host, port);
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
    public void askForInfo(String string){
        connection.sendPacket(string);
    }

    @Override
    public void packReceived(Object obj) {
        if (obj instanceof UpdatePacket) {
            UpdatePacket packet = (UpdatePacket) obj;

            fields = packet.getFields();
            currentPlayer = packet.getCurrentPlayer();
        } else if (obj instanceof String) {
            System.out.println(obj);
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

    @Override
    public void addPlayer(int port, String name) {

    }

}
