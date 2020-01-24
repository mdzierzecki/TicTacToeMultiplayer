package net;

import game.Game;
import packets.ClientPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientGame extends Game {

    private Socket socket;

    private PacketsHandler packetsHandler;
    private ObjectOutputStream out;


    public ClientGame() {
        super(Game.PLAYER_TWO);
    }


    @Override
    public void connect(int port, String host) {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Trying to send msg...");
            out.writeObject("Inside connect method: client");
            System.out.println("Msg sent!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
//        System.out.println(x + " | " + y);
//
//        if(isMyTurn()) {
//            this.sendPacket(new ClientPacket(x, y));
//        }

    }

    @Override
    public void askForInfo(String string){
        System.out.println(string + " 2");
        System.out.println("Trying to send skForInfo...");
        this.sendPacket(string);
        System.out.println("After skForInfo!");
    }

    public void sendPacket(Object object) {
        try {
            System.out.println(object + " 3");
            out.writeObject(object);
        } catch (IOException e) {
            System.out.println("FAILED!!!");
            e.printStackTrace();
        }
    }

    @Override
    public void packReceived(Object obj) {
//        System.out.println(obj + " 7");
//        if (obj instanceof UpdatePacket) {
//            UpdatePacket packet = (UpdatePacket) obj;
//
//            fields = packet.getFields();
//            currentPlayer = packet.getCurrentPlayer();
//        } else if (obj instanceof String) {
//            System.out.println(obj);
//        }
//
//        gameWindow.repaint();
    }

    @Override
    public void close() {
//        try {
//            clientHandler.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void addPlayer(int port, String name) {

    }

}
