package tk.shanebee.redSpawner;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Events implements Listener {

    private static RedSpawner plugin;
    Events(RedSpawner instance) {
        plugin = instance;
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent e) {
        Block spawner = e.getSpawner().getBlock();
        if(!spawner.isBlockPowered()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreakSpawner(BlockBreakEvent e) {
        if(plugin.getConfig().getBoolean("Silk Spawners.Enabled")) {
            Player p = e.getPlayer();
            ItemStack tool = p.getInventory().getItemInMainHand();
            Block block = e.getBlock();
            if ((block.getType() == Material.SPAWNER) &&
                    (tool != null) &&
                    (tool.containsEnchantment(Enchantment.SILK_TOUCH))) {
                if (p.hasPermission("redspawner.spawner.break")) {
                    World w = e.getBlock().getWorld();
                    Location loc = block.getLocation().add(0.0D, 0.5D, 0.0D);
                    CreatureSpawner spawner = (CreatureSpawner) block.getState();
                    ItemStack item = new ItemStack(Material.SPAWNER, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.AQUA + "Mob Spawner");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(spawner.getSpawnedType().toString());
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    w.dropItem(loc, item);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlaceSpawner(BlockPlaceEvent e) {
        if (plugin.getConfig().getBoolean("Silk Spawners.Enabled")) {
            Block block = e.getBlock();
            Material mat = block.getType();
            Player p = e.getPlayer();
            if (mat == Material.SPAWNER) {
                if (p.hasPermission("redspawner.spawner.place")) {
                    ItemStack item = e.getItemInHand();
                    String itemName = item.getItemMeta().getDisplayName();
                    if (itemName.equals(ChatColor.AQUA + "Mob Spawner")) {
                        String mobType = ChatColor.stripColor(item.getItemMeta().getLore().get(0));
                        CreatureSpawner spawner = ((CreatureSpawner) e.getBlockPlaced().getState());
                        spawner.setSpawnedType(EntityType.valueOf(mobType));

                        spawner.update();
                    }
                } else e.setCancelled(true);
            }
        }
    }

}
