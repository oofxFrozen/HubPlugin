package thelonebarkeeper.mgame.lobby.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BossBarManager {

    private static BossBar bossBar;

    public static void setupBossBar() {
        bossBar = Bukkit.createBossBar(ChatColor.LIGHT_PURPLE + "SkyWars 1/2/7", BarColor.WHITE, BarStyle.SOLID);
    }

    public static BossBar getBossBar() {
        return bossBar;
    }
}
