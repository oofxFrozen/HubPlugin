package thelonebarkeeper.mgame.lobby.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryManager {

    public static void setupInventory (Player player) {
        Inventory inventory = player.getInventory();
        inventory.clear();

        ItemMeta meta;

        ItemStack leave = new ItemStack(Material.MAGMA_CREAM);
        meta = leave.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.BOLD + "Выйти в хаб");
        leave.setItemMeta(meta);
        inventory.setItem(8, leave);


        Material material = Material.getMaterial("WATCH");
        if (material == null)
            material = Material.LEGACY_WATCH;
        ItemStack hide = new ItemStack(material);
        meta = hide.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.BOLD + "Скрыть игроков");
        hide.setItemMeta(meta);
        inventory.setItem(7, hide);

    }
}
