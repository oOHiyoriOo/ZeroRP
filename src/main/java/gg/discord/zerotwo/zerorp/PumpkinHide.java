package gg.discord.zerotwo.zerorp;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.logging.Logger;

public class PumpkinHide implements Listener {
    private final Main plugin;
    private Logger log;
    private FileConfiguration conf;

    public PumpkinHide(Main plugin, Logger log){
        this.plugin = plugin;
        this.log = log;
        this.conf = this.plugin.getConfig();
    }

    @EventHandler
    public void onArmorWear(InventoryClickEvent e) {
        if( e.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL) ){
            if(e.isShiftClick() &&
                    e.getCurrentItem() != null &&
                    e.getCurrentItem().getType().equals(Material.CARVED_PUMPKIN)){
                e.setCancelled(true);
            }else if(e.getAction().equals(InventoryAction.PLACE_ALL) &&
                    e.getSlotType().equals(InventoryType.SlotType.ARMOR)){
                if (e.getCursor().getType() != null &&
                        e.getCursor().getType()
                        .equals(Material.CARVED_PUMPKIN)) {

                    Player target = (Player)e.getWhoClicked();

                    for( Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams() ){
                        if( team.hasPlayer(target) ){
                            log.info("Set Invisible for: "+team.getName());
                            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
                        }
                    }

                    // target.setCustomNameVisible(false);
                    // ArmorStand stand = (ArmorStand) target.getWorld().spawnEntity(target.getLocation(),EntityType.ARMOR_STAND);
                    // stand.setInvulnerable(true);
                    // stand.setVisible(false);
                    // stand.setMarker(true);
                    
                    // target.addPassenger(stand);
                }
            }else if(e.getAction().equals(InventoryAction.PICKUP_ALL) &&
                    e.getSlotType().equals(InventoryType.SlotType.ARMOR)){
                if (e.getCurrentItem().getType() != null &&
                        e.getCurrentItem().getType()
                        .equals(Material.CARVED_PUMPKIN)) {
                    Player target = (Player)e.getWhoClicked();

                    for( Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams() ){
                        if( team.hasPlayer(target) ){
                            log.info("Set Invisible for: "+team.getName());
                            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
                        }
                    }

                    // target.setCustomNameVisible(true);
                    // ArrayList<Entity> Passengers = new ArrayList<>(target.getPassengers());
                    // for(Entity passenger : Passengers ){
                    //     if(passenger.getType().equals(EntityType.ARMOR_STAND)){
                    //         target.removePassenger(passenger);
                    //         passenger.remove();
                    //     }
                    // }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player target = e.getPlayer();
        ArrayList<Entity> Passengers = new ArrayList<>(target.getPassengers());
        for(Entity passenger : Passengers ){
            if(passenger.getType().equals(EntityType.ARMOR_STAND)){
                target.removePassenger(passenger);
                passenger.remove();
            }
        }
    }
}
