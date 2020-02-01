package gui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoWindow extends JPanel implements ActionListener {

    private Game game;


    private JLabel portLabel;
    private JTextArea portTextArea;

    private JLabel hostLabel;
    private JTextArea hostTextArea;

    private JLabel nameLabel;
    private JTextArea nameTextArea;

    private JButton connectButton;
    private JButton infoButton;
    private JButton playButton;
    private JButton logoutButton;

    private JLabel statusLabel;
    private JLabel statusText;

    private JLabel whosTurnLabel;
    private JLabel whosTurnText;

    private JList jList;


    public InfoWindow(Game game) {
        this.game = game;
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();

        // host Label
        hostTextArea = new JTextArea();
        hostLabel = new JLabel();

        hostTextArea.setColumns(20);
        hostTextArea.setLineWrap(true);
        hostTextArea.setRows(2);
        hostTextArea.setWrapStyleWord(true);
        hostTextArea.setEditable(true);

        hostLabel.setText("Host: ");


        // port Label
        portTextArea = new JTextArea();
        portLabel = new JLabel();

        portTextArea.setColumns(20);
        portTextArea.setLineWrap(true);
        portTextArea.setRows(2);
        portTextArea.setWrapStyleWord(true);
        portTextArea.setEditable(true);

        portLabel.setText("Port: ");


        // name Label
        nameTextArea = new JTextArea();
        nameLabel = new JLabel();

        nameTextArea.setColumns(20);
        nameTextArea.setLineWrap(true);
        nameTextArea.setRows(2);
        nameTextArea.setWrapStyleWord(true);
        nameTextArea.setEditable(true);

        nameLabel.setText("Name: ");


        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);

        infoButton = new JButton("Players list");
        infoButton.addActionListener(this);

        playButton = new JButton("Play");
        playButton.addActionListener(this);
        playButton.setEnabled(false);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setEnabled(false);

        statusLabel = new JLabel();
        statusLabel.setText("Connection: ");

        statusText = new JLabel();
        statusText.setText("Disconnected");
        statusText.setForeground(Color.red);


        whosTurnLabel = new JLabel();
        whosTurnLabel.setText("Status: ");

        whosTurnText = new JLabel();
        whosTurnText.setText(" - ");


        gbc.fill = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;


        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(hostLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(hostTextArea, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(portLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(portTextArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(nameTextArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(connectButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(playButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        this.add(infoButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        this.add(logoutButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(statusLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(statusText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(whosTurnLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        this.add(whosTurnText, gbc);


        String empty[]= {""};
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 40;      //make this component tall
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.weighty = 2;
        jList = new JList(empty);
        this.add(jList, gbc);


        this.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Connect")) {
            if (portTextArea.getText().equals("") || hostTextArea.getText().equals("") || nameTextArea.getText().equals("")) {
                statusText.setText("Wrong request");
            } else {
                Map<String, String> playerInfo = new HashMap<>();
                playerInfo.put("name", nameTextArea.getText());
                game.connect(Integer.parseInt(portTextArea.getText()), hostTextArea.getName(), playerInfo);
                logoutButton.setEnabled(true);
                playButton.setEnabled(true);
            }

        } else if (s.equals("Players list")) {
            game.sendRequest("playersinfo");

            try {
                Thread.sleep((long) (Math.random()*500));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }


            DefaultListModel model = new DefaultListModel();

            for (ArrayList list : game.playersInfo)
            {
                String result = "";
                for(Object data : list){
                    result += data + "       ";
                }
                model.addElement(result);
            }

            jList.setModel(model);

        } else if (s.equals("Play")) {
            if (portTextArea.getText().equals("") || hostTextArea.getText().equals("") || nameTextArea.getText().equals("")) {
                statusText.setText("Wrong request");
            } else {
                game.sendRequest("joinme");
                logoutButton.setEnabled(false);
            }

        } else if (s.equals("Logout")) {

            game.sendRequest("logout");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            game.close();

        } else {
            statusText.setText("Wrong request");
        }


    }

    public void setStatus(String status) {
        if (status.equals("connected")) {
            statusText.setText("Connected");
            statusText.setForeground(new Color(0, 143, 2));
            connectButton.setEnabled(false);
        } else if (status.equals("waitingForOpponent")) {
            statusText.setText("Waiting for opponent");
            statusText.setForeground(new Color(143, 129, 0));
            playButton.setEnabled(false);
        } else if (status.equals("opponentFound")) {
            statusText.setText("We found opponent!");
            statusText.setForeground(new Color(0, 64, 255));

            try {
                Thread.sleep((long) (Math.random()*3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            statusText.setText("Play!");
            statusText.setForeground(new Color(143, 0, 119));
            playButton.setEnabled(false);
        }
    }


    public void setTurn(String msg){
        System.out.println(msg);
        if(msg.equals("mine")){
            whosTurnText.setText("Your turn");
            whosTurnText.setForeground(new Color(58, 65, 255));
        } else if(msg.equals("opponent")){
            whosTurnText.setText("Opponent's turn");
            whosTurnText.setForeground(Color.black);
        } else if(msg.contains("Result")) {
            whosTurnText.setText(msg);
            whosTurnText.setForeground(Color.black);

            logoutButton.setEnabled(true);

            playButton.setText("New game");
            playButton.setEnabled(true);
        }oko

    }
}
