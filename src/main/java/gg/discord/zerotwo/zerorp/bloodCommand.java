package gg.discord.zerotwo.zerorp;

import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public class bloodCommand implements CommandExecutor {
    private final Main plugin;
    private Logger log;
    private PlayerParticlesAPI ppAPI;

    public bloodCommand(Main plugin, Logger log, PlayerParticlesAPI ppAPI){
        this.plugin = plugin;
        this.log = log;
        this.ppAPI = ppAPI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.equals(Bukkit.getServer().getConsoleSender())){
            Player target;
            try{
                target = Bukkit.getPlayer(args[0]);
            }catch (ArrayIndexOutOfBoundsException e)  {
                sender.sendMessage("§4§l┇ §7Bitte einen User angeben!");
                return false;
            } catch (NullPointerException e){
                sender.sendMessage("§4§l┇ §7Bitte einen User angeben der online ist!");
                return false;
            }

            if(this.ppAPI != null && target != null){
                int i = 0;
                while (i < 7) {
                    ppAPI.addActivePlayerParticle(target, ParticleEffect.DUST, DefaultStyles.TRAIL, new OrdinaryColor(91,0,158));
                    i++;
                }

                target.sendMessage("Du bist nun vom Bösen gezeichnet.");
                PersistentDataContainer targetData = target.getPersistentDataContainer();
                targetData.set(new NamespacedKey(this.plugin,"curse"), PersistentDataType.INTEGER,1);

                new BukkitRunnable(){
                    public void run(){
                        if(targetData.get(new NamespacedKey(plugin,"curse"), PersistentDataType.INTEGER) != null &&
                        targetData.get(new NamespacedKey(plugin,"curse"), PersistentDataType.INTEGER) == 1){
                            ppAPI.resetActivePlayerParticles(target);
                        }
                    }
                }.runTaskLater(this.plugin,20L*900);

            }else{
                log.warning("ppAPI is null.");
            }
            return false;
        }else{
            sender.sendMessage("this is a console only command!");
            return false;
        }
    }
}
