package thelonebarkeeper.mgame.lobby;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import thelonebarkeeper.mgame.lobby.Data.Data;
import thelonebarkeeper.mgame.lobby.Data.DataManager;
import thelonebarkeeper.mgame.lobby.Listeners.GameJoinListener;
import thelonebarkeeper.mgame.lobby.Listeners.PlayerChatListener;
import thelonebarkeeper.mgame.lobby.Listeners.PlayerStatsReceive;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;

    public static Lobby getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        setupListeners();

        try {
            setupDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);




    }


    private void setupListeners() {
        Bukkit.getPluginManager().registerEvents(new GameJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new GameJoinListener());
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PlayerStatsReceive());
    }

    private void setupDirectories() throws IOException {
        File playerdata = new File("./src/main/resources/player_data.yml");

        boolean dataExists = playerdata.createNewFile();

        InputStream inputStream = new FileInputStream(playerdata);

        Yaml yaml = new Yaml();
        Map<String, Object> dataRaw = yaml.load(inputStream);
        for (String name : dataRaw.keySet()) {
            String[] values = dataRaw.get(name).toString().split(",");
            int kills = Integer.parseInt(values[0]);
            int games = Integer.parseInt(values[1]);
            int wins = Integer.parseInt(values[2]);
            Data data = new Data(name, kills, games, wins);

            DataManager.getPlayerData().put(name, data);
        }
    }

    private void saveData() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("./src/main/resources/player_data.yml");
        Yaml yaml = new Yaml();
        HashMap<String, String> dataToSave = new HashMap<>();
        for (String name : DataManager.getPlayerData().keySet()) {
            dataToSave.put(name, DataManager.getPlayerData().get(name).toString());
        }
        yaml.dump(dataToSave, writer);
    }
}