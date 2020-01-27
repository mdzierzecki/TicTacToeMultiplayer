package net;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayersInfo implements Serializable {

    ArrayList<ArrayList<String>> playersInfo;

    public PlayersInfo() {
        this.playersInfo = new ArrayList<>();
    }

    public void add(ArrayList<String> list){
        playersInfo.add(list);
    }

    public ArrayList<ArrayList<String>> getPlayersInfo() {
        return playersInfo;
    }

    public void removePlayer(String name) {
        for(ArrayList list: playersInfo) {
            String str = (String)list.get(0);
            if(str.contains(name)) {
                playersInfo.remove(list);
            }
        }
    }

    @Override
    public String toString() {
        return "PlayersInfo{" +
                "playersInfo=" + playersInfo +
                '}';
    }
}
