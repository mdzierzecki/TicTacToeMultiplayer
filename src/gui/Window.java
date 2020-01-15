package gui;

import game.Game;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends JFrame {

    private Game game;

    public Window(Game game, String title, int width, int height) {
        super(title);
        this.game = game;
        setResizable(false);
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new Listener());

        GridLayout layout = new GridLayout(1, 2);
        setLayout(layout);

    }

    class Listener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            game.close();
        }
    }
}
