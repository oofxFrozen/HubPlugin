package thelonebarkeeper.mgame.lobby.Listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import thelonebarkeeper.mgame.lobby.Lobby;


public class GameJoinListener implements Listener {

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

        player.sendPluginMessage(Lobby.getInstance(), "SkyWarsConnect", out.toByteArray());

    }



    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        event.setQuitMessage("");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(player.getWorld().getSpawnLocation().add(0.5f, 0, 0.5f));
        event.setJoinMessage("");
    }






}
