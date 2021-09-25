package thelonebarkeeper.mgame.lobby.Listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import thelonebarkeeper.mgame.lobby.Data.Data;
import thelonebarkeeper.mgame.lobby.Data.DataManager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PlayerStatsReceive implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("Forward")) {

            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            try {
                String[] data =  msgin.readUTF().split(",");
                String name = data[0];
                int kills = Integer.parseInt(data[1]);
                boolean isWinner = Boolean.getBoolean(data[2]);

                Data playerData = DataManager.getPlayerData().get(name);
                playerData.addGame();
                if (isWinner)
                    playerData.addWin();
                playerData.addKills(kills);

                DataManager.getPlayerData().put(name, playerData);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
