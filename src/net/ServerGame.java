package net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGame {

    private ServerSocket serverSocket;
    private Socket socket;


    private static ArrayList<ServerHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);


    public ServerGame() {
        try {
            serverSocket = new ServerSocket(7999);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection");
                socket = serverSocket.accept();
                System.out.println("[SERVER] Connected to client");
                ServerHandler clientThread = new ServerHandler(socket);

                clients.add(clientThread);
                pool.execute(clientThread);

                System.out.println("Connected");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


//    @Override
//    public void inputReceived(int x, int y) {
//
//        if(isMyTurn()) {
//            updateField(x, y);
//
//        }
//        gameWindow.repaint();
//    }



//    @Override
//    public void packReceived(Object obj) {
//        if (obj instanceof ClientPacket) {
//            ClientPacket packet = (ClientPacket) obj;
//
//            updateField(packet.getX(), packet.getY());
//        } else if (obj instanceof String) {
//            System.out.println("Ty chuju server");
//            String aa = "Ala";
//            packetsHandler.sendPacket(aa);
//        }
//
//        gameWindow.repaint();
//    }
//
//    private void updateField(int x, int y) {
//        System.out.println(x + " | " + y);
//        if(fields[x][y] == Game.NOBODY) {
//            fields[x][y] = currentPlayer;
//            if(currentPlayer == Game.PLAYER_ONE) {
//                currentPlayer = Game.PLAYER_TWO;
//            } else if (currentPlayer == Game.PLAYER_TWO) {
//                currentPlayer = Game.PLAYER_ONE;
//            }
//
//            packetsHandler.sendPacket(new UpdatePacket(fields, currentPlayer));
//        }
//
//    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
