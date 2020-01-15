package game;

import gui.GameWindow;
import gui.InfoWindow;
import gui.Window;
import net.ConnectionsInfo;

import javax.swing.*;
import java.awt.*;

public abstract class Game {

    // Pytania:
    // 1. Czy server moze sie uruchamiac od razu jako gra?
    // 2. Punjt 2.1.5 - zawiłe to. Pokazać rozgrywke i zapyac
    // 3. Czy mozna wyslac projekt IntelliJ?
    // 4. Czy zdazy ocenic przed egzaminem?

    public static final int  PORT = 6999;

    public static final int WIDTH = 1200, HEIGHT = 600;
    public static final int FIELD_WIDTH = 600 / 3, FIELD_HEIGHT = HEIGHT / 3;


    public static final int NOBODY = 0, PLAYER_ONE = 1, PLAYER_TWO = 2;
    protected int[][] fields;

    private Window window;
    protected GameWindow gameWindow;
    protected InfoWindow infoWindow;

    protected int currentPlayer;

    protected int thisPlayer;

    ConnectionsInfo connectionsInfo;

    public Game(int thisPlayer) {
        this.thisPlayer = thisPlayer;
        window = new Window(this, "Kółko i krzyżyk łonlajn", WIDTH, HEIGHT);


        gameWindow = new GameWindow(this);
        infoWindow = new InfoWindow(this);

        fields = new int[3][3];

        window.add(gameWindow);
        window.add(infoWindow);
        window.setVisible(true);

        currentPlayer = Game.PLAYER_ONE;
    }

    protected boolean isMyTurn() {
        if(thisPlayer == currentPlayer) {
            return true;
        }
        return false;
    }

    public abstract void inputReceived(int x, int y);

    public abstract void packReceived(Object obj);

    public abstract void close();

    public int[][] getFields() {
        return fields;
    }
}
