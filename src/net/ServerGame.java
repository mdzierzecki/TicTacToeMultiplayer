package net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGame {

    private ServerSocket serverSocket;
    private Socket socket;
    public static final int PORT = 8999;

    private static ArrayList<ServerHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();

    PlayersInfo playersInfo = new PlayersInfo();

    private static ArrayList<ServerHandler> readyClients = new ArrayList<>();


    public ServerGame() {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection on port " + PORT + " on localhost");
                socket = serverSocket.accept();
                ServerHandler clientThread = new ServerHandler(socket, this);
                clientThread.sendPacket("connected");
                clients.add(clientThread);
                pool.execute(clientThread);
                System.out.println("[SERVER] Connected to client");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addReadyClient(ServerHandler client) {
        readyClients.add(client);
        connectClients();
    }

    private void connectClients() {
        if(readyClients.size() >= 2) {
            readyClients.get(0).addOpponent(readyClients.get(1));
            readyClients.get(1).addOpponent(readyClients.get(0));

            int playerX = (int)Math.round(Math.random()) + 1;

            readyClients.get(0).sendPacket(playerX);
            if (playerX==1) readyClients.get(1).sendPacket(2); else readyClients.get(1).sendPacket(1);

            readyClients.get(0).sendPacket("opponentFound");
            readyClients.get(1).sendPacket("opponentFound");

            readyClients.remove(0);
            readyClients.remove(0);
        }
    }

    public void logoutPlayer(String name){
        playersInfo.removePlayer(name);
    }

    public void readPlayerInfo(Map<String, String> playerInfo) {
        ArrayList<String> newPlayer = new ArrayList<>();
        newPlayer.add(playerInfo.get("name"));
        newPlayer.add(playerInfo.get("port"));
        newPlayer.add(playerInfo.get("ip"));

        playersInfo.add(newPlayer);

    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
