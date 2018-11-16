package tk.shanebee.redSpawner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComp implements TabCompleter {

    private static RedSpawner plugin;
    TabComp(RedSpawner instance) {
        plugin = instance;
    }

    private static final List<String> BLANK = Arrays.asList("");
    private static final List<String> NOPLAYER = Arrays.asList(ChatColor.RED + "<-- ERROR: NOT AN ONLINE PLAYER");
    private static final List<String> COMMANDSSS = Arrays.asList("give", "about", "reload");
    private static final List<String> COMMANDS = Arrays.asList("about", "reload");
    private static final List<EntityType> NoMobs = Arrays.asList(EntityType.AREA_EFFECT_CLOUD,
            EntityType.ARMOR_STAND, EntityType.ARROW, EntityType.BOAT, EntityType.COMPLEX_PART, EntityType.DRAGON_FIREBALL,
            EntityType.DROPPED_ITEM, EntityType.EGG, EntityType.ENDER_CRYSTAL, EntityType.ENDER_PEARL, EntityType.ENDER_SIGNAL,
            EntityType.ENDER_DRAGON, EntityType.EVOKER_FANGS, EntityType.EXPERIENCE_ORB, EntityType.FALLING_BLOCK,
            EntityType.FIREBALL, EntityType.FIREWORK, EntityType.FISHING_HOOK, EntityType.GIANT, EntityType.GHAST,
            EntityType.ITEM_FRAME, EntityType.LEASH_HITCH, EntityType.LIGHTNING, EntityType.LINGERING_POTION, EntityType.LLAMA_SPIT,
            EntityType.MINECART, EntityType.MINECART_CHEST, EntityType.MINECART_COMMAND, EntityType.MINECART_FURNACE,
            EntityType.MINECART_HOPPER, EntityType.MINECART_MOB_SPAWNER, EntityType.MINECART_TNT, EntityType.PAINTING,
            EntityType.PRIMED_TNT, EntityType.SHULKER_BULLET, EntityType.SMALL_FIREBALL, EntityType.SNOWBALL, EntityType.SPECTRAL_ARROW,
            EntityType.SPLASH_POTION, EntityType.THROWN_EXP_BOTTLE, EntityType.TIPPED_ARROW, EntityType.TRIDENT,
            EntityType.UNKNOWN, EntityType.WITHER_SKULL);

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length == 1) {
            if (plugin.getConfig().getBoolean("Silk Spawners.Enabled")) {
                return StringUtil.copyPartialMatches(args[0], COMMANDSSS, new ArrayList<>());
            } else {
                return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());
            }
        }
        if(args.length == 2) {
            if (args[0].equalsIgnoreCase("give") && plugin.getConfig().getBoolean("Silk Spawners.Enabled")) {
                List<String> players = new ArrayList<>();
                for (Player type : Bukkit.getOnlinePlayers()) {
                    players.add(type.getName());
                }
                return StringUtil.copyPartialMatches(args[1], players, new ArrayList<>());
            }
        }
        if(args.length == 3) {
            if (args[0].equals("give") && plugin.getConfig().getBoolean("Silk Spawners.Enabled")) {
                Player target = Bukkit.getServer().getPlayer(args[1]);
                if(target instanceof Player) {
                    List<String> mobs = new ArrayList<>();
                    for(EntityType type : EntityType.values()) {
                        if(type.isSpawnable() && !NoMobs.contains(type)) {
                        mobs.add(type.name()); }
                    }
                    return StringUtil.copyPartialMatches(args[2], mobs, new ArrayList<>());
                } else return StringUtil.copyPartialMatches(args[2], NOPLAYER, new ArrayList<>());
            }
        }
        return BLANK;
    }

}
