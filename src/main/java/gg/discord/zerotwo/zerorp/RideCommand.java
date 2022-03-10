package gg.discord.zerotwo.zerorp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class RideCommand implements CommandExecutor {
    private final Main plugin;
    private Logger log;
    public RideCommand (Main plugin, Logger log){
        this.plugin = plugin;
        this.log = log;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target;
        Entity source = (Entity)sender;

        if(sender.hasPermission("zerorp.ride")){
            try{
                target = Bukkit.getPlayer(args[0]);
                log.info("Trying to sit on: "+target.getName());
                if(target != null){
                    target.addPassenger(source);
                    log.info("Succeeded.");
                }
            }catch (ArrayIndexOutOfBoundsException e)  {
                sender.sendMessage("§4§l┇ §7Bitte einen User angeben!");
                return false;
            } catch (NullPointerException e){
                sender.sendMessage("§4§l┇ §7Bitte einen User angeben der online ist!");
                return false;
            }
        }
        return false;
    }
}
