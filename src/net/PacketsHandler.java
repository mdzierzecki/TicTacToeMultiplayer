package net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public abstract class PacketsHandler implements Runnable{

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private boolean running;


    public PacketsHandler(Socket socket) {

        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        running = true;

        while (running) {
            try {
                Object object = inputStream.readObject();
                packReceived(object);
            } catch (EOFException | SocketException e) {
                running = false;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendPacket(Object object) {
        try {
            outputStream.reset();
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {

        inputStream.close();
        outputStream.close();
    }

    public abstract void packReceived(Object obj);


}
