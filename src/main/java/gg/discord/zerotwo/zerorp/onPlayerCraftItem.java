package gg.discord.zerotwo.zerorp;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public final class onPlayerCraftItem implements Listener {
    private final Main plugin;
    private Logger log;

    public onPlayerCraftItem(Main plugin, Logger log){
        this.plugin = plugin;
        this.log = log;
    }

    @EventHandler
    public void PlayerCraftItem(CraftItemEvent e){
        ItemStack Recipe = Bukkit.getRecipe(new NamespacedKey(this.plugin, "xp_book")).getResult();
        ItemStack Crafting = e.getRecipe().getResult();
        if( Recipe.equals(Crafting) &&
            e.isShiftClick()){
            e.setCancelled(true);
        }else if(Recipe.equals(Crafting)){
            BookMeta bookMeta = (BookMeta) Crafting.getItemMeta();
            // WHYYYYYY ?!??!!?
            List<String> lore = new ArrayList<String>();
            lore.add("ID: "+new Random().nextInt());
            bookMeta.setLore(lore);
            Crafting.setItemMeta(bookMeta);
            e.setCurrentItem(Crafting);
        }
    }
}
