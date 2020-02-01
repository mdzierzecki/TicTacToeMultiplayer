package game;

import gui.GameWindow;
import gui.InfoWindow;
import gui.Window;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

public abstract class Game {

    public static final int WIDTH = 1200, HEIGHT = 600;
    public static final int FIELD_WIDTH = 600 / 3, FIELD_HEIGHT = HEIGHT / 3;


    public static final int NOBODY = 0, PLAYER_ONE = 1, PLAYER_TWO = 2;
    protected int[][] fields;

    private Window window;
    protected GameWindow gameWindow;
    protected InfoWindow infoWindow;

    protected int currentPlayer;

    protected int thisPlayer;

    public ArrayList<ArrayList<String>> playersInfo;


    public Game() {
        window = new Window(this, "Tic Tac Toe", WIDTH, HEIGHT);


        gameWindow = new GameWindow(this);
        infoWindow = new InfoWindow(this);

        fields = new int[3][3];

        window.add(gameWindow);
        window.add(infoWindow);
        window.setVisible(true);

        currentPlayer = Game.PLAYER_ONE;

        playersInfo = new ArrayList<>();
    }

    protected void showWinner(int winner){
//        if(winner == Game.NOBODY) {
//            JOptionPane.showMessageDialog(null, "TIE!");
//        } else {
//            JOptionPane.showMessageDialog(null, "The player " + winner + " has won the game!");
//        }

    }

    protected boolean isMyTurn() {
        if(thisPlayer == currentPlayer) {
            return true;
        }
        return false;
    }




    public abstract void connect(int port, String host, Map<String, String> playerInfo);

    public abstract void inputReceived(int x, int y);

    public abstract void sendRequest(String string);

    public abstract void addPlayer(ArrayList<ArrayList<String>> playersInfo);

    public abstract void close();

    public int[][] getFields() {
        return fields;
    }
}
