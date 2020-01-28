package game;

import net.ClientHandler;
import packets.ClientPacket;
import packets.GameEndPacket;
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
        if(isMyTurn()) {
            updateField(x, y);
        }


    }

    private void updateField(int x, int y) {
        if(fields[x][y] == Game.NOBODY) {
            fields[x][y] = currentPlayer;
            if(currentPlayer == Game.PLAYER_ONE) {
                currentPlayer = Game.PLAYER_TWO;
            } else if (currentPlayer == Game.PLAYER_TWO) {
                currentPlayer = Game.PLAYER_ONE;
            }
            clientHandler.sendPacket(new UpdatePacket(fields, currentPlayer));
            setTurn();

            gameWindow.repaint();

            checkResult();
        }

    }

    public void checkResult(){
        int winner = checkWinner();
        if(winner != Game.NOBODY) {
            endGame(winner);
        } else {
            int emptyCount = 0;

            for(int a=0; a<3; a++) {
                for(int b=0; b<3; b++) {
                    if(fields[a][b] == Game.NOBODY) {
                        emptyCount++;
                    }
                }
            }

            if(emptyCount == 0) {
                endGame(winner);
            }
        }
    }

    private void endGame(int winner){
        showWinner(winner);
        clientHandler.sendPacket(new GameEndPacket(winner));
    }

    public void showWinner(int winner){
        if(winner == Game.NOBODY) {
            infoWindow.setTurn("Result: TIE!");
        } else {
            if(this.thisPlayer==winner) {
                infoWindow.setTurn("Result: You have won!");
            } else {
                infoWindow.setTurn("Result: You lose!");
            }
        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        super.showWinner(winner);
    }


    @Override
    public void sendRequest(String string){
        clientHandler.sendPacket(string);
    }

    public void set(UpdatePacket packet){
        fields = packet.getFields();
        currentPlayer = packet.getCurrentPlayer();
        checkResult();
        gameWindow.repaint();
        setTurn();

    }


    @Override
    public void close() {
        try {
            clientHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }



    public void statusUpdate(String status){
        infoWindow.setStatus(status);
    }

    public void addPlayer(ArrayList<ArrayList<String>> playersInfo) {
        super.playersInfo = playersInfo;
    }

    public void setTurn(){
        if(isMyTurn()){
            infoWindow.setTurn("mine");
        } else {
            infoWindow.setTurn("opponent");
        }
    }

    private int checkWinner() {
        for(int player = Game.PLAYER_ONE; player <= Game.PLAYER_TWO; player++) {
            for(int y=0; y<3; y++) {
                int playerCount = 0;

                for(int x=0; x<3; x++) {
                    if(fields[x][y] == player){
                        playerCount++;
                    }
                }

                if(playerCount == 3) {
                    return player;
                }
            }

            for(int x=0; x<3; x++) {
                int playerCount = 0;

                for (int y=0; y<3; y++) {
                    if(fields[x][y] == player) {
                        playerCount++;
                    }
                }

                if(playerCount == 3) {

                }
            }

            int playerCount = 0;
            for(int coordinate=0; coordinate<3; coordinate++) {
                if(fields[coordinate][coordinate] == player) {
                    playerCount++;
                }
            }

            if(playerCount == 3) {
                return player;
            }

            playerCount = 0;

            for (int coordinate=0; coordinate<3; coordinate++) {
                if(fields[2-coordinate][coordinate] == player) {
                    playerCount++;
                }
            }

            if(playerCount == 3) {
                return player;
            }

        }

        return Game.NOBODY;
    }


}
