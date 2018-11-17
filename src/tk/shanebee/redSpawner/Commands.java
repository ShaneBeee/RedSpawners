package tk.shanebee.redSpawner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Commands implements CommandExecutor{

    private PluginDescriptionFile pdfFile = RedSpawner.getPlugin(RedSpawner.class).getDescription();

    private static RedSpawner plugin;
    Commands(RedSpawner instance) {
        plugin = instance;
    }

    private String prefix = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "RedSpawner" +
            ChatColor.GRAY + "]";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getServer().getPlayer(args[1]);
                if(!plugin.getConfig().getBoolean("Silk Spawners.Enabled")) {
                    String msg = plugin.getConfig().getString("Silk Spawners.Disabled Command Message");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    return false;
                }
                if(target instanceof Player) {

                    String mobName = args[2].toUpperCase();
                    EntityType type = null;

                    try {
                        type = EntityType.valueOf(mobName);
                    } catch (IllegalArgumentException exp) {
                        sender.sendMessage("THAT IS NOT A MOB");
                        return false;
                    }

                    ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
                    ItemMeta meta = spawner.getItemMeta();
                    ArrayList<String> lore = new ArrayList<>();
                    meta.setDisplayName(ChatColor.AQUA + "Mob Spawner");
                    lore.add(mobName);
                    meta.setLore(lore);
                    spawner.setItemMeta(meta);


                    target.getInventory().addItem(spawner);
                    if(target == sender) {
                        sender.sendMessage(prefix + ChatColor.GOLD + " You received a new spawner: "
                        + ChatColor.AQUA + mobName);
                    } else {
                    sender.sendMessage(prefix + ChatColor.GOLD + " You gave a new spawner: "
                            + ChatColor.AQUA + mobName + ChatColor.GOLD + " To: " + ChatColor.AQUA + target); }
                } else {
                    sender.sendMessage(prefix + ChatColor.RED + " That player is not online");
                }
            }
        } else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("about")) {
                sender.sendMessage("\n" + prefix + ChatColor.AQUA + " Plugin Info");
                sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GRAY +  pdfFile.getVersion());
                sender.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.GRAY +  pdfFile.getAuthors());
                sender.sendMessage(ChatColor.GOLD + "Website: " + ChatColor.AQUA + pdfFile.getWebsite());

            } else if(args[0].equalsIgnoreCase("reload")) {
                RedSpawner.getPlugin(RedSpawner.class).reloadConfig();
                sender.sendMessage(prefix + ChatColor.GREEN + " Config reloaded successfully");
            } else if(args[0].equalsIgnoreCase("give")) {
                sender.sendMessage(prefix + ChatColor.AQUA + " Correct Usage: /redspawner give <player> <mob>");
            } else sender.sendMessage(prefix + ChatColor.AQUA + " Correct Usage: " + cmd.getUsage());
        }
        return true;
    }

    @EventHandler
    public void onTest(PlayerInteractEvent e) {
        ItemStack pot = new ItemStack(Material.POTION);
        ItemMeta meta = pot.getItemMeta();
        PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, 1, 1);
        e.getPlayer().addPotionEffect(effect);


    }
}
