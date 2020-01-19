package net;

import game.Game;
import packets.ClientPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerGame extends Game {

    private ServerSocket serverSocket;
    private Socket socket;

    Connection connection;

    Map<String, ArrayList<String>> connectionsMap = new HashMap<>();


    public ServerGame() {
        super(Game.PLAYER_ONE);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                connection = new Connection(this, socket);
                System.out.println("Connected");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void addPlayer(int port, String name) {

    }

    @Override
    public void connect(int port, String host) {

    }

    @Override
    public void inputReceived(int x, int y) {

        if(isMyTurn()) {
            updateField(x, y);

        }
        gameWindow.repaint();
    }



    @Override
    public void packReceived(Object obj) {
        if (obj instanceof ClientPacket) {
            ClientPacket packet = (ClientPacket) obj;

            updateField(packet.getX(), packet.getY());
        } else if (obj instanceof String) {
            System.out.println("Ty chuju server");
            String aa = "Ala";
            connection.sendPacket(aa);
        }

        gameWindow.repaint();
    }

    private void updateField(int x, int y) {
        System.out.println(x + " | " + y);
        if(fields[x][y] == Game.NOBODY) {
            fields[x][y] = currentPlayer;
            if(currentPlayer == Game.PLAYER_ONE) {
                currentPlayer = Game.PLAYER_TWO;
            } else if (currentPlayer == Game.PLAYER_TWO) {
                currentPlayer = Game.PLAYER_ONE;
            }

            connection.sendPacket(new UpdatePacket(fields, currentPlayer));
        }

    }

    @Override
    public void close() {
        try {
            connection.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // niepotrzebne



    @Override
    public void askForInfo(String string) {

    }
}
