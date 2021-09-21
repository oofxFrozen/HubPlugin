package thelonebarkeeper.mgame.lobby.Listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

//TODO: make data receiver.   SPLIT STRING WITH ','. RECEIVE IT IN String.format("%s,%d,%b", playerName, kills, isWinner).

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
                String somedata =  msgin.readUTF(); // Read the data in the same way you wrote it
                short somenumber = msgin.readShort();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
