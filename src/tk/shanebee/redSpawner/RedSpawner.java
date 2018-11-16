package tk.shanebee.redSpawner;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class RedSpawner extends JavaPlugin implements Listener {

    private FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        String pl = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "RedSpawner" +
                ChatColor.GRAY + "]";
        String enable = ChatColor.GREEN + "Successfully loaded";
        this.getServer().getConsoleSender().sendMessage(pl + " " + enable);

        this.getServer().getPluginManager().registerEvents(new Events(this), this);
        this.getCommand("redspawner").setExecutor(new Commands(this));
        this.getCommand("redspawner").setTabCompleter(new TabComp(this));
        Configuration.loadConfig(config);
        saveConfig();
    }

    @Override
    public void onDisable() {
        String pl = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "RedSpawner" +
                ChatColor.GRAY + "]";
        String disable = ChatColor.GREEN + "Successfully unloaded";
        this.getServer().getConsoleSender().sendMessage(pl + " " + disable);
    }


}
