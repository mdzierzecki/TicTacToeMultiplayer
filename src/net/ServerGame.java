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


    private static ArrayList<ServerHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();

    PlayersInfo playersInfo = new PlayersInfo();


    private static ArrayList<ServerHandler> readyClients = new ArrayList<>();


    public ServerGame() {
        try {
            serverSocket = new ServerSocket(8999);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection");
                socket = serverSocket.accept();
                System.out.println("[SERVER] Connected to client");
                ServerHandler clientThread = new ServerHandler(socket, this);
                clientThread.sendPacket("connected");
                clients.add(clientThread);
                pool.execute(clientThread);

                System.out.println("Connected");

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

            readyClients.get(0).sendPacket("opponentFound");
            readyClients.get(0).sendPacket(1);

            readyClients.get(1).sendPacket("opponentFound");
            readyClients.get(1).sendPacket(2);

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

        System.out.println(playersInfo);

    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
