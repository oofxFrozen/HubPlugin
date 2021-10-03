package thelonebarkeeper.mgame.lobby;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import thelonebarkeeper.mgame.lobby.Data.Data;
import thelonebarkeeper.mgame.lobby.Data.DataManager;
import thelonebarkeeper.mgame.lobby.Listeners.GameJoinListener;

import java.io.*;
import java.util.HashMap;

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

        setupDirectories();
        try {
            setupData();
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

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "SkyWarsConnect");
    }

    public void setupDirectories() {
        if (instance.getDataFolder().mkdir())
            Bukkit.getServer().getLogger().info("Plugin directories are successfully created.");
    }

    public void setupData() throws IOException {

        File playerdata = new File(this.getDataFolder().getPath() + "/player_data.yml/");

        boolean dataExists = playerdata.createNewFile();

        InputStream inputStream = new FileInputStream(playerdata);

        Yaml yaml = new Yaml();
        HashMap<String, Object> dataRaw;
        dataRaw = yaml.load(inputStream);

        if (dataRaw == null)
            return;

        for (String name : dataRaw.keySet()) {
            String[] values = dataRaw.get(name).toString().split(",");
            int kills = Integer.parseInt(values[0]);
            int games = Integer.parseInt(values[1]);
            int wins = Integer.parseInt(values[2]);
            Data data = new Data(name, kills, games, wins);

            DataManager.getPlayerData().put(name, data);
        }
    }

    public void saveData() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(this.getDataFolder().getPath() + "/player_data.yml/");
        Yaml yaml = new Yaml();
        HashMap<String, String> dataToSave = new HashMap<>();
        for (String name : DataManager.getPlayerData().keySet()) {
            dataToSave.put(name, DataManager.getPlayerData().get(name).toString());
        }
        yaml.dump(dataToSave, writer);
    }
}