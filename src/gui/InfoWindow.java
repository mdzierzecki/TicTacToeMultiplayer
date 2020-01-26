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

    private JButton submitButton;

    private JButton infoButton;

    private JButton playButton;

    private JButton sendButton;


    JList jList;

    private ArrayList<ArrayList<String>> playersInfo;


    public InfoWindow(Game game) {
        this.game = game;

        // host Label
        hostTextArea = new JTextArea();
        hostLabel = new JLabel();

        hostTextArea.setColumns(20);
        hostTextArea.setLineWrap(true);
        hostTextArea.setRows(2);
        hostTextArea.setWrapStyleWord(true);
        hostTextArea.setEditable(true);

        hostLabel.setText("Enter host: ");


        // port Label
        portTextArea = new JTextArea();
        portLabel = new JLabel();

        portTextArea.setColumns(20);
        portTextArea.setLineWrap(true);
        portTextArea.setRows(2);
        portTextArea.setWrapStyleWord(true);
        portTextArea.setEditable(true);

        portLabel.setText("Enter port: ");


        // name Label
        nameTextArea = new JTextArea();
        nameLabel = new JLabel();

        nameTextArea.setColumns(20);
        nameTextArea.setLineWrap(true);
        nameTextArea.setRows(2);
        nameTextArea.setWrapStyleWord(true);
        nameTextArea.setEditable(true);

        nameLabel.setText("Enter name: ");


        submitButton = new JButton("Join");
        submitButton.addActionListener(this);

        infoButton = new JButton("Info");
        infoButton.addActionListener(this);

        playButton = new JButton("Play");
        playButton.addActionListener(this);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);



        jList = new JList();

        this.add(jList);

        this.add(hostLabel);
        this.add(hostTextArea);

        this.add(portLabel);
        this.add(portTextArea);

        this.add(nameLabel);
        this.add(nameTextArea);


        this.add(submitButton);
        this.add(infoButton);

        this.add(playButton);
        this.add(sendButton);


        this.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Join")) {

            System.out.println(" "+ portTextArea.getText() + " " + hostTextArea.getText());

            Map<String, String> playerInfo = new HashMap<>();
            playerInfo.put("name", nameTextArea.getText());
            game.connect(Integer.parseInt(portTextArea.getText()), hostTextArea.getName(), playerInfo);

        } else if (s.equals("Info")) {
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
