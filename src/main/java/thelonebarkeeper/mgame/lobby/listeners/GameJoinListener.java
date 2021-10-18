package thelonebarkeeper.mgame.lobby.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import thelonebarkeeper.mgame.lobby.Lobby;
import thelonebarkeeper.mgame.lobby.managers.BossBarManager;
import thelonebarkeeper.mgame.lobby.managers.InventoryManager;

import java.util.HashMap;
import java.util.Objects;


public class GameJoinListener implements Listener {

    public static HashMap<Player, Boolean> areHidden = new HashMap<>();

    Lobby instance = Lobby.getInstance();

    @EventHandler
    public void onPlayerPortalJump(PlayerPortalEvent event) {
        event.setCancelled(true);
        event.getPlayer().stopSound(Sound.BLOCK_PORTAL_TRAVEL);
        event.getPlayer().stopSound(Sound.BLOCK_PORTAL_TRIGGER);

    }

    @EventHandler
    public void onPlayerPortalKek(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (player.getLocation().getBlock().getType().toString().contains("PORTAL")) {
            player.teleport(player.getWorld().getSpawnLocation());
            connect(player);
        }
    }

    private void connect(Player player) {

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF( "Connect");

        player.sendPluginMessage(instance, "SkyWarsConnect", out.toByteArray());

    }



    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        event.setQuitMessage("");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        setupPlayer(event.getPlayer());
        event.setJoinMessage("");
    }


    BukkitScheduler scheduler = Bukkit.getScheduler();
    public void setupPlayer(Player player) {
        if (player == null) {
            scheduler.runTaskLater(instance, () -> setupPlayer(player), 1);
            return;
        }

        InventoryManager.setupInventory(player);
        player.teleport(player.getWorld().getSpawnLocation().add(0.5f, 0, 0.5f));
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        BossBarManager.getBossBar().addPlayer(player);

        for (Player gamer : areHidden.keySet()) {
            if (areHidden.get(gamer)) {
                gamer.hidePlayer(instance, player);
            }
        }

    }




}
