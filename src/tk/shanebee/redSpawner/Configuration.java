package tk.shanebee.redSpawner;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;

@SuppressWarnings("WeakerAccess")
public class Configuration implements Listener {

    public static void loadConfig(FileConfiguration config) {
        PluginDescriptionFile pdfFile = RedSpawner.getPlugin(RedSpawner.class).getDescription();

        config.addDefault("Silk Spawners.Enabled", false);
        config.addDefault("Silk Spawners.Disabled Command Message", "&cSilk spawners is disabled, this can be enabled in the config");

        config.options().copyDefaults(true);

        config.options().header("RedSpawner\n" + "Version: " + pdfFile.getVersion() + "\n\n" +
                "Silk Spawners: Enable this if you are not using a Silk Spawners type plugin\n" +
                "and would like to use the Silk Spawners functionality\n");
    }
}
