package gui;

import game.Game;
import res.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JPanel {

    private Game game;

    public GameWindow(Game game) {
        this.game = game;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.inputReceived(e.getX() / Game.FIELD_WIDTH, e.getY() / Game.FIELD_HEIGHT);
                }
            }
        });

        this.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(10));

        for (int x = Game.FIELD_WIDTH; x <= Game.FIELD_WIDTH *2;  x += Game.FIELD_WIDTH) {
            g2d.drawLine(x, 0, x, Game.HEIGHT);

        }

        for (int y = Game.FIELD_HEIGHT; y <= Game.FIELD_HEIGHT *2;  y += Game.FIELD_HEIGHT) {
            g2d.drawLine(0, y, Game.WIDTH, y);
        }


        for(int x=0; x<3; x++) {
            for(int y=0; y<3; y++) {
                int field = game.getFields()[x][y];
                if (field != Game.NOBODY) {
                    System.out.println(x + y);
                        g2d.drawImage(Resources.letters[field - 1], x * Game.FIELD_WIDTH, y * Game.FIELD_HEIGHT,
                                Game.FIELD_WIDTH - 5, Game.FIELD_HEIGHT - 5, null);
                }

            }
        }

    }




}



