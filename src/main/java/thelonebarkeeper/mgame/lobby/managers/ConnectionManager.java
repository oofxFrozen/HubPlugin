package thelonebarkeeper.mgame.lobby.managers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import thelonebarkeeper.mgame.lobby.Lobby;

public class ConnectionManager {

    @Deprecated
    public static void sendPlayer (Player player, String server) {

    }

    public static void sendPlayerHub (Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("LOOTCORE");
        player.sendPluginMessage(Lobby.getInstance(), "BungeeCord", out.toByteArray());
    }
}
