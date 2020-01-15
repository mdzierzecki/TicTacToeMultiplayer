package gui;

import game.Game;

import javax.swing.*;
import java.awt.*;

public class InfoWindow extends JPanel {

    private Game game;

    public InfoWindow(Game game) {
        this.game = game;

        JButton OKButton = new JButton("OK");

        this.add(OKButton);

        this.setBorder(BorderFactory.createLineBorder(Color.black));

    }



}
