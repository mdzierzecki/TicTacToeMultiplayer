package packets;

import java.io.Serializable;

public class ClientPacket implements Serializable {

    private int x;
    private int y;

    public ClientPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "ClientPacket{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
