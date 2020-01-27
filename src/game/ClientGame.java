package game;

import net.ClientHandler;
import packets.ClientPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientGame extends Game {

    private Socket socket;
    public ClientHandler clientHandler;
    private static ExecutorService pool = Executors.newSingleThreadExecutor();
    private ArrayList<ArrayList<String>> playersInfo;


    public ClientGame() { playersInfo = new ArrayList<>(); }

    public void setPlayer(int player){ this.thisPlayer = player; }

    @Override
    public void connect(int port, String host, Map<String, String> playerInfo) {
        try {
            socket = new Socket(host, port);
            clientHandler = new ClientHandler(socket, this);
            pool.execute(clientHandler);
            playerInfo.put("port", "" + port);

            InetAddress ip = InetAddress.getLocalHost();
            playerInfo.put("ip", ip.toString());
            clientHandler.sendPacket(playerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        System.out.println(x + " |x " + y);

        if(isMyTurn()) {
            updateField(x, y);
//            clientHandler.sendPacket(new ClientPacket(x, y));
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

            clientHandler.sendPacket(new UpdatePacket(fields, currentPlayer));
        }

    }


    @Override
    public void askForInfo(String string){
        clientHandler.sendPacket(string);
    }

    public void set(UpdatePacket packet){
        fields = packet.getFields();
        currentPlayer = packet.getCurrentPlayer();
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

    public void statusUpdate(String status){
        infoWindow.setStatus(status);
    }


    public void addPlayer(ArrayList<ArrayList<String>> playersInfo) {
        super.playersInfo = playersInfo;
    }
}
