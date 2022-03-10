package gg.discord.zerotwo.zerorp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public final class WealthTax implements Listener {
    private final Main plugin;
    private Logger log;
    private FileConfiguration conf;

    public WealthTax(Main plugin, Logger log){
        this.plugin = plugin;
        this.log = log;

        this.conf = this.plugin.getConfig();

    }
}
