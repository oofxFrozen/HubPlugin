package thelonebarkeeper.mgame.lobby.Data;

import java.util.HashMap;

public class DataManager {

    private static HashMap<String, Data> playerData = new HashMap<>();


    public static HashMap<String, Data> getPlayerData() {
        return playerData;
    }
}
