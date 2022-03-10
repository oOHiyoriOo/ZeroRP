package gg.discord.zerotwo.zerorp;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.logging.Logger;

public final class cookies implements Listener {
    private final Main plugin;
    private Logger log;

    public cookies(Main plugin, Logger log){
        this.plugin = plugin;
        this.log = log;
    }



    @EventHandler
    public void InfinityCookieUse(PlayerItemConsumeEvent e){

        if( e.getItem().getType().equals(Material.COOKIE)){
            ItemStack cookie = e.getItem();
            PersistentDataContainer cookieData = cookie.getItemMeta().getPersistentDataContainer();
            if( cookieData.get(new NamespacedKey(this.plugin,"infinity"), PersistentDataType.DOUBLE) != null &&
                    cookieData.get(new NamespacedKey(this.plugin,"infinity"), PersistentDataType.DOUBLE) == 1){
                // give the player the saturation.
                Player target = e.getPlayer();

                if(target.getFoodLevel() < 19){
                    target.setFoodLevel(target.getFoodLevel() + 4);
                }else if(target.getFoodLevel() < 20 && target.getFoodLevel() >= 19){
                    target.setFoodLevel(target.getFoodLevel() + 1);
                    target.setSaturation(target.getSaturation() + 20);
                }else{
                    target.setSaturation(target.getSaturation() + 1);
                }
                e.setCancelled(true);
            }
        }
    }
}