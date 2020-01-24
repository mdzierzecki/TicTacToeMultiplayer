package net;

import game.Game;
import packets.ClientPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientGame extends Game {

    private Socket socket;
    public ClientHandler clientHandler;

    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public ClientGame() {
        super(Game.PLAYER_TWO);

    }

    @Override
    public void connect(int port, String host) {
        try {
            socket = new Socket(host, port);
            clientHandler = new ClientHandler(socket);
            pool.execute(clientHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        System.out.println(x + " | " + y);

        if(isMyTurn()) {
            clientHandler.sendPacket(new ClientPacket(x, y));
        }

    }

    @Override
    public void askForInfo(String string){
        clientHandler.sendPacket(string);
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
            clientHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayer(int port, String name) {

    }

}
