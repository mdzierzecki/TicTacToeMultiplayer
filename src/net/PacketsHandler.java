package net;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class PacketsHandler implements Runnable {

    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean running;


    public PacketsHandler(Socket socket) {
        this.client = socket;
        try {
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        running = true;

        while (running) {
            try {
                Object o = in.readObject();
                System.out.println("Read object: "+o);
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


        public void packReceived(Object obj) {
        if (obj instanceof String) {

            String aa = " " + 2;
            this.sendPacket(aa);


        }


//        gameWindow.repaint();
    }


    public void sendPacket(Object object) {
        try {
            out.reset();
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {

        in.close();
        out.close();
    }


}




