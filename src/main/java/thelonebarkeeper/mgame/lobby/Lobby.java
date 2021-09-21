package thelonebarkeeper.mgame.lobby;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import thelonebarkeeper.mgame.lobby.Listeners.GameJoinListener;
import thelonebarkeeper.mgame.lobby.Listeners.PlayerChatListener;
import thelonebarkeeper.mgame.lobby.Listeners.PlayerStatsReceive;

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
}