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

    JLabel statusLabel;
    JLabel statusText;

    JList jList;

    private ArrayList<ArrayList<String>> playersInfo;


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

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        statusLabel = new JLabel();
        statusLabel.setText("Status: ");

        statusText = new JLabel();
        statusText.setText("Disconnected");
        statusText.setForeground(Color.red);

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
        this.add(logoutButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        this.add(playButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        this.add(infoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(statusLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(statusText, gbc);


        String week[]= { "Monday","Tuesday","Wednesday",
                "Thursday","Friday","Saturday","Sunday"};
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 40;      //make this component tall
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 2;
        gbc.weighty = 2;
        jList = new JList(week);
        this.add(jList, gbc);


        this.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Connect")) {

            System.out.println(" "+ portTextArea.getText() + " " + hostTextArea.getText());

            Map<String, String> playerInfo = new HashMap<>();
            playerInfo.put("name", nameTextArea.getText());
            game.connect(Integer.parseInt(portTextArea.getText()), hostTextArea.getName(), playerInfo);

        } else if (s.equals("Players list")) {
            String info = "playersinfo";
            game.askForInfo(info);

            try {
                Thread.sleep((long) (Math.random()*500));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println(game.playersInfo);

            DefaultListModel model = new DefaultListModel();


            for (ArrayList list : game.playersInfo)
            {
                for(Object data : list)
                    model.addElement(data + "\t");
            }

            jList.setModel(model);
        } else if (s.equals("Play")) {
            String info = "joinme";
            game.askForInfo(info);
            System.out.println("Wait for opponent");

        } else if (s.equals("Send")) {
            String info = "[msg] dupa";
            game.askForInfo(info);

        }


    }



}
