package thelonebarkeeper.mgame.lobby.Listeners;

import com.google.common.io.ByteArrayDataInput;
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
import org.bukkit.plugin.messaging.PluginMessageListener;
import thelonebarkeeper.mgame.lobby.Lobby;

public class GameJoinListener implements Listener, PluginMessageListener {

    Lobby instance = Lobby.getInstance();

    @EventHandler
    public void onPlayerPortalJump(PlayerPortalEvent event) {
        event.setCancelled(true);
        event.getPlayer().stopSound(Sound.BLOCK_PORTAL_TRAVEL);
        event.getPlayer().stopSound(Sound.BLOCK_PORTAL_TRIGGER);

    }

    @EventHandler
    public void onPlayerPortalKek(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getBlock().getType().toString().contains("PORTAL")) {
            event.getPlayer().teleport(event.getPlayer().getLocation().add(0, 1, 0));
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PlayerCount");
            out.writeUTF("skywars-1");
            event.getPlayer().sendPluginMessage(instance, "BungeeCord", out.toByteArray());
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        String subchannel = in.readUTF();
        String server = in.readUTF(); // Name of server, as given in the arguments
        if (subchannel.equalsIgnoreCase("PlayerCount") && server.contains("skywars")) {
            int playercount = in.readInt();
            if (playercount < 1) {
                out.writeUTF("Connect");
                out.writeUTF(server);
                player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
            } else {
                out.writeUTF("PlayerCount");
                int serverNum = Integer.parseInt(server.split("skywars-")[0]) + 1;
                if (serverNum > 9) {
                    return;
                }
                out.writeUTF("skywars-" + serverNum);
                player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
            }
        }

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
