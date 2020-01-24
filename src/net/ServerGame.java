package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGame {

    private ServerSocket listener;
    private Socket socket;

    private static ArrayList<PacketsHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);


    private int playersAmount = 0;


    public ServerGame() {

        try {
            listener = new ServerSocket(6999);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection");
                socket = listener.accept();
                System.out.println("[SERVER] Connected to client");

                PacketsHandler clientThread = new PacketsHandler(socket);
                clients.add(clientThread);

                pool.execute(clientThread);

                playersAmount += 1;
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
//            connection.sendPacket(new UpdatePacket(fields, currentPlayer));
//        }
//
//    }


    public void close() {
        try {
//            client.close();
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
