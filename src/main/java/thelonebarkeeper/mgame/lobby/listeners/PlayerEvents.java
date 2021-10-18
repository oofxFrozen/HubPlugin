package thelonebarkeeper.mgame.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import thelonebarkeeper.mgame.lobby.Lobby;
import thelonebarkeeper.mgame.lobby.managers.ConnectionManager;

import java.util.HashMap;

public class PlayerEvents implements Listener {

    HashMap<Player, Boolean> canUse = new HashMap<>();

    @EventHandler
    public void onPlayerDamageTake (EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRMB (PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        canUse.putIfAbsent(player, true);
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK || !canUse.get(player))
            return;
        useCycle(player);

        Material item = player.getInventory().getItemInMainHand().getType();

        if (item == Material.MAGMA_CREAM) {
            ConnectionManager.sendPlayerHub(player);
        }

        if (item.toString().contains("WATCH")) {
            Boolean areHidden = GameJoinListener.areHidden.get(player);

            if (areHidden == null)
                areHidden = false;

            String msg = areHidden ? "Игроки более не скрыты." : "Игроки скрыты.";
            player.sendMessage(msg);

            Lobby lobby = Lobby.getInstance();
            for (Player gamer : Bukkit.getServer().getOnlinePlayers()) {
                if (areHidden)
                    player.showPlayer(lobby, gamer);
                else {
                    if (gamer != player)
                        player.hidePlayer(lobby, gamer);
                }

                GameJoinListener.areHidden.put(player, !areHidden);
            }
        }

    }

    public void useCycle(Player player) {
        canUse.put(player, false);
        Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), () -> {
            canUse.put(player, true);
        }, 20);
    }

    @EventHandler
    public void onPlayerF (PlayerSwapHandItemsEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInventoryClick (InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }
}
