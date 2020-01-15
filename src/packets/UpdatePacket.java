package packets;

import java.io.Serializable;

public class UpdatePacket implements Serializable {

    private int[][] fields;

    private int currentPlayer;

    public UpdatePacket(int[][] fields, int currentPlayer) {
        this.fields = fields;
        this.currentPlayer = currentPlayer;
    }

    public int[][] getFields() {
        return fields;
    }

    public void printFields() {
        for(int i=0; i<fields.length; i++) {
            for(int j=0; j<fields[i].length; j++) {
                System.out.println(fields[i][j]);
            }
        }
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
